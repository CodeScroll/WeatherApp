package com.example.jordan.weatherapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GetStories extends AsyncTask<String, Integer, ArrayList<Story>>
{
    private Activity mActivity;
    private ListView mList;
    private ArrayList<Story> mStories = new ArrayList();
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    GetStories(Activity activity, ListView list) {
        mActivity = activity;
        mList = list;
    }
    @Override
    protected ArrayList<Story> doInBackground(String... params) {
        String url = params[0];
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
            URL url1 = new URL(url);
// Parse the xml
            Document doc = db.parse(new InputSource(url1.openStream()));
            doc.getDocumentElement().normalize();
// Get all <item> tags.
            NodeList nodelist = doc.getElementsByTagName("item");
            int length = nodelist.getLength();
            for (int i = 0; i < length; i++) {
                Node currentNode = nodelist.item(i);
                Element fstElmnt = (Element) currentNode;
                NodeList titleList = fstElmnt.getElementsByTagName("title");
                Element titleElement = (Element) titleList.item(0);
                titleList = titleElement.getChildNodes();
                NodeList dateList = fstElmnt.getElementsByTagName("pubDate");
                Element dateElement = (Element) dateList.item(0);
                dateList = dateElement.getChildNodes();
                NodeList contentList =
                        fstElmnt.getElementsByTagName("description");
                Element contentElement = (Element) contentList.item(0);
                contentList = contentElement.getChildNodes();
                mStories.add(new Story(((Node)
                        titleList.item(0)).getNodeValue(),
                        ((Node) dateList.item(0)).getNodeValue(),
                        ((Node) contentList.item(0)).getNodeValue()));
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return mStories;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(ArrayList<Story> result) {
        super.onPostExecute(result);
        StoriesAdapter adapter = new StoriesAdapter(mActivity, result);
        mList.setAdapter(adapter);
    }
}