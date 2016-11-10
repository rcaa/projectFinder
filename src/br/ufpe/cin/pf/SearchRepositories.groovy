package br.ufpe.cin.pf

import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach;

import groovy.json.JsonSlurper

class SearchRepositories {

	static final String url = "https://api.github.com/search/repositories?"

	String executeSearch(Search s) {
		def url = new URL(url + "q=" + s.query + "+" + "in:" + s.keyword
				+ "+" + "language:" + s.programmingLanguage + "+"
				+ "forks:>=" + s.minimumForks + "+"
				+ "stars:>=" + s.minimumStars
				+ "&sort=stars&order=desc")
		return url.getText()
	}
	
	def parseJson(String json) {
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(json)
		def listUrls = object.items.html_url
		int i = 1
		for (url in listUrls) {
			println url
			i++
		}
	}

	static main(args) {
		Search search = new Search(query:"java", programmingLanguage:"java",
		minimumStars:100, minimumForks:50, keyword:"readme")
		SearchRepositories s = new SearchRepositories()
		def json = s.executeSearch(search)
		s.parseJson(json)
	}
}
