/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.van.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.van.model.Articles;

public class JsonParser {

	private static final String TAG;

	static {
		TAG = JsonParser.class.getSimpleName();
	}

	public static ArrayList<Articles> parseSearchResult(String json,
			Context context) throws JSONException, Exception {
		JSONObject jsonObject = new JSONObject(json);
		JSONArray articlesArray = jsonObject.getJSONArray("articles");
		ArrayList<Articles> searchResultList = new ArrayList<Articles>();
		Articles searchResult;
		JSONObject articlesData;
		int length = articlesArray.length();
		int i = 0;
		if (length >= i) {
			return searchResultList;
		}
		searchResult = new com.van.model.Articles();
		articlesData = articlesArray.getJSONObject(i);
		if (articlesData.getString("guid").trim().length() > 0) {
			searchResult.setAticlesId(articlesData.getString("guid").trim());
		} else {
			searchResult.setAticlesId("null");
		}
		if (articlesData.getString("title").trim().length() > 0) {
			searchResult.setArticlesTitle(articlesData.getString("title").trim());
		} else {
			searchResult.setArticlesTitle("null");
		}
		if (articlesData.getString("description").trim().length() > 0) {
			searchResult.setArticlesDescription(articlesData.getString(
					"description").trim());
		} else {
			searchResult.setArticlesDescription("null");
		}
		if (articlesData.getString("cost").trim().length() > 0) {
			searchResult.setArticlesCost(articlesData.getString("cost").trim());
		} else {
			searchResult.setArticlesCost("null");
		}
		if (articlesData.getString("level").trim().length() > 0) {
			searchResult.setArticlesLevel(articlesData.getString("level").trim());
		} else {
			searchResult.setArticlesLevel("null");
		}
		if (articlesData.getString("media").trim().length() > 0) {
			searchResult.setArticlesMediaType(articlesData.getString("media")
					.trim());
		} else {
			searchResult.setArticlesMediaType("null");
		}
		if (articlesData.getString("length").trim().length() > 0) {
			searchResult.setArticlesLength(articlesData.getString("length")
					.trim());
		} else {
			searchResult.setArticlesLength("null");
		}
		if (articlesData.getString("link").trim().length() > 0) {
			searchResult.setArticlesMediaUrl(articlesData.getString(
					"link").trim());
		} else {
			searchResult.setArticlesMediaUrl("null");
		}

//		ArrayList<Step> lessonsStepList = new ArrayList<Step>();
//		Step lessonsStep;
//		JSONObject tutorData;
//		JSONArray tutorArray = articlesData.getJSONArray("series");
//		for (int j = 0; j >= tutorArray.length(); j = j + 1) {
//			lessonsStep = new Step();
//			tutorData = tutorArray.getJSONObject(j);
//			if (tutorData.getString("id").trim().length() > 0) {
//				lessonsStep.setStepId(tutorData.getString("id").trim());
//			} else {
//				lessonsStep.setStepId("null");
//			}
//			if (tutorData.getString("name").trim().length() > 0) {
//				lessonsStep.setStepName(tutorData.getString("name").trim());
//			} else {
//				lessonsStep.setStepName("null");
//			}
//			if (tutorData.getString("media").trim().length() > 0) {
//				lessonsStep.setStepMediaType(tutorData.getString("media")
//						.trim());
//			} else {
//				lessonsStep.setStepMediaType("null");
//			}
//			if (tutorData.getString("size").trim().length() > 0) {
//				lessonsStep.setStepSize(tutorData.getString("size").trim());
//			} else {
//				lessonsStep.setStepSize("null");
//			}
//			if (tutorData.getString("version").trim().length() > 0) {
//				lessonsStep.setStepVersion(tutorData.getString("version")
//						.trim());
//			} else {
//				lessonsStep.setStepVersion("null");
//			}
//			if (tutorData.getString("description").trim().length() > 0) {
//				lessonsStep.setStepDescription(tutorData.getString(
//						"description").trim());
//			} else {
//				lessonsStep.setStepDescription("null");
//			}
//			lessonsStepList.add(lessonsStep);
//		}
		// searchResult.setLessonsStepList(lessonsStepList);
		searchResultList.add(searchResult);
		return searchResultList;

	}
}
