package com.shane.me.shanedemo.config;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.shane.me.shanedemo.model.MainItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Asus on 2017/5/20.
 */

public class MainContentConfig {

    public static List<MainItem> parseMainContentConfig(Context context) {
        InputStream inputStream = null;

        try {
            inputStream = context.getAssets().open("config/content_list.xml");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            MainContentHandler handler = new MainContentHandler();
            parser.parse(inputStream, handler);

            return handler.getMainContentList();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new ArrayList<>();
    }

    private static final class MainContentHandler extends DefaultHandler {

        private static final String KEY_LIST = "list";
        private static final String KEY_ITEM = "item";
        private static final String KEY_TITLE = "title";
        private static final String KEY_ACTION = "action";
        private static final String KEY_INTENT = "intent";
        private static final String KEY_COMPONENT_NAME = "component_name";
        private static final String KEY_EXTRAS = "extras";
        private static final String KEY_EXTRA = "extra";

        List<MainItem> result = new ArrayList<>();
        MainItem tmp;
        String value;
        boolean parseIntent;
        String extraKey;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (KEY_LIST.equals(localName)) {
                result.clear();
            } else if (KEY_ITEM.equals(localName)) {
                tmp = new MainItem();
            } else if (KEY_INTENT.equals(localName)) {
                parseIntent = true;
            } else if (KEY_EXTRAS.equals(localName)) {
                if (tmp != null) {
                    tmp.extras = new Bundle();
                }
            } else if (KEY_EXTRA.equals(localName)) {
                extraKey = attributes.getValue("name");
            }

            value = "";
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            value = new String(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (KEY_ITEM.equals(localName)) {
                if (!TextUtils.isEmpty(tmp.title)) {
                    result.add(tmp);
                }
                tmp = null;
            } else if (KEY_TITLE.equals(localName)) {
                if (tmp != null) {
                    tmp.title = value;
                }
            } else if (KEY_INTENT.equals(localName)) {
                parseIntent = false;
            } else if (KEY_ACTION.equals(localName)) {
                if (parseIntent && tmp != null && !TextUtils.isEmpty(value)) {
                    tmp.action = value;
                }
            } else if (KEY_COMPONENT_NAME.equals(localName)) {
                if (parseIntent && tmp != null) {
                    tmp.componentName = value;
                }
            } else if (KEY_EXTRA.equals(localName)) {
                if (tmp != null && tmp.extras != null) {
                    if (!TextUtils.isEmpty(extraKey)) {
                        tmp.extras.putString(extraKey, value);
                    }
                }
                extraKey = "";
            }
        }


        public List<MainItem> getMainContentList() {
            return result;
        }


    }







}
