package br.ufpe.cin.pf

import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach;

import groovy.json.JsonSlurper

class SearchRepositories {

	static final String url = "https://api.github.com/search/repositories?"

	String executeSearch(Search s, int page) {
		def url = new URL(this.url + "q=" + s.query + "+" + "in:" + s.keyword
				+ "+" + "language:" + s.programmingLanguage + "+"
				+ "forks:>=" + s.minimumForks + "+"
				+ "stars:>=" + s.minimumStars
				+ "&sort=stars&order=desc&page=" + page)
		return url.getText()
	}

	def parseJson(String json) {
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(json)
		def items = object.items
		int i = 1
		for (item in items) {
			println i + ": " + item.html_url
			i++
		}
	}

	static main(args) {
		Search search = new Search(query:"java", programmingLanguage:"java",
		minimumStars:100, minimumForks:50, keyword:"readme", numPages:10)
		//numero de desenvolvedores simultaneos
		SearchRepositories s = new SearchRepositories()
		StringBuffer sb = new StringBuffer()
		def page = 1
		while (page <= search.numPages) {
			def json = s.executeSearch(search, page)
			sb.append(json)
			s.parseJson(json)
			page++
		}
	}
}
