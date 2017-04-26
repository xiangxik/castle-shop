package com.whenling.shop.support.setting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.whenling.castle.web.ServletSupport;

@Component
@ServletSupport
public class SettingService {

	@Autowired
	private CacheManager cacheManager;

	@Value("${setting.xml_path?:/setting.xml}")
	private String settingXmlPath;

	@Autowired
	private ConversionService conversionService;

	public Setting get() {
		Cache cache = cacheManager.getCache(Setting.CACHE_NAME);
		ValueWrapper valueWrapper = cache.get(Setting.CACHE_KEY);
		Setting setting = null;
		if (valueWrapper != null) {
			Object cacheValue = valueWrapper.get();
			setting = (Setting) cacheValue;
		} else {
			setting = Setting.getInstance();
			try {
				File settingFile = new ClassPathResource(settingXmlPath).getFile();
				Document document = new SAXReader().read(settingFile);
				@SuppressWarnings("unchecked")
				List<Element> elements = document.selectNodes("/config/setting");
				BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(setting);
				for (Element element : elements) {
					String name = element.attributeValue("name");
					String value = element.attributeValue("value");
					beanWrapperImpl.setPropertyValue(name,
							conversionService.convert(value, beanWrapperImpl.getPropertyType(name)));
				}
			} catch (IOException | DocumentException e) {
				e.printStackTrace();
			}
			cache.put(Setting.CACHE_KEY, setting);
		}
		return setting;
	}

	public void set(Setting setting) {
		try {
			File settingFile = new ClassPathResource(settingXmlPath).getFile();
			Document document = new SAXReader().read(settingFile);
			@SuppressWarnings("unchecked")
			List<Element> elements = document.selectNodes("/config/setting");
			BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(setting);
			for (Element element : elements) {
				String name = element.attributeValue("name");
				String value = conversionService.convert(beanWrapperImpl.getPropertyValue(name), String.class);
				Attribute attribute = element.attribute("value");
				attribute.setValue(value);
			}

			FileOutputStream fileOutputStream = null;
			XMLWriter xmlWriter = null;
			try {
				OutputFormat outputFormat = OutputFormat.createPrettyPrint();
				outputFormat.setEncoding("UTF-8");
				outputFormat.setIndent(true);
				outputFormat.setIndent("	");
				outputFormat.setNewlines(true);
				fileOutputStream = new FileOutputStream(settingFile);
				xmlWriter = new XMLWriter(fileOutputStream, outputFormat);
				xmlWriter.write(document);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (xmlWriter != null) {
					try {
						xmlWriter.close();
					} catch (IOException e) {
					}
				}
				IOUtils.closeQuietly(fileOutputStream);
			}
			Cache cache = cacheManager.getCache(Setting.CACHE_NAME);
			cache.put(Setting.CACHE_KEY, setting);
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}
}
