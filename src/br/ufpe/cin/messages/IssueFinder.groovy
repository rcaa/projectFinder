package br.ufpe.cin.messages

import java.awt.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach;

import groovy.json.JsonSlurper

class IssueFinder {

	static final String url = "https://api.github.com/repos/gitblit/gitblit/issues?"
	static final String initialDate = "2000-01-01T00:00:01Z"

	String executeSearch(int page) {
		def url = new URL(this.url + "since=" + this.initialDate
				+ "&state=all&sort=comments&page=" + page)
		return url.getText()
	}

	def parseJson(String json) {
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(json)
		def list = new ArrayList()
		object.each { list.add(it.number) }
		list
	}
	
	def getListOfIssueNumbers() {
		def finalList = new ArrayList()
		def page = 1
		while (page <= 10) {
			def json = executeSearch(page)
			finalList.addAll(parseJson(json))
			page++
		}
		finalList.each {
			print it + ','
		}
	}
	
	static main(args) {
		IssueFinder d = new IssueFinder()
		d.getListOfIssueNumbers()
	}
}
