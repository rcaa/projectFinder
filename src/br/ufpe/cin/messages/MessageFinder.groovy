package br.ufpe.cin.messages

import groovy.json.JsonSlurper
import br.ufpe.cin.pf.Search
import br.ufpe.cin.pf.SearchRepositories

class MessageFinder {

	static final String url = "https://api.github.com/repos/gitblit/gitblit/issues/"
	static final String initialDate = "2000-01-01T00:00:01Z"

	String executeSearch(int issueNumber) {
		def url = new URL(this.url + issueNumber + '/comments?' + 'since=' + this.initialDate)
		return url.getText()
	}

	def parseJson(String json, String filePath) {
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(json)
		object.each {
			writeToFile(filePath, it.body)
		}
	}

	def writeToFile(def filePath, def body) {
		def fileOutput = new File(filePath)
		fileOutput << body + '\n'
	}

	static main(args) {
		MessageFinder s = new MessageFinder()
		StringBuffer sb = new StringBuffer()
		IssueFinder iF = new IssueFinder()
		def listIssueNumbers = [301,1168,511,713,402,345,422,761,1043,751,344,394,399,492,527,562,332,799,320,495,921,85,307,697,166,920,545,591,521,310,721,964,700,734,974,775,412,466,716,620,298,220,327,448,348,673,229,461,561,598,478,453,555,1179,719,161,312,570,602,424,324,581,485,517,393,1237,359,619,1009,1166,942,563,340,623,1108,474,663,1205,627,313,8,404,579,1022,1067,742,1076,780,812,355,647,680,653,798,421,533,535,1013,7,572,667,930,454,1134,809,551,607,779,552,411,458,584,744,432,433,678,644,518,259,414,415,559,1025,1016,926,361,418,754,688,339,1119,658,501,401,1148,450,949,374,269,242,773,1165,702,1167,116,704,429,674,853,431,706,388,354,1158,226,587,813,1055,934,589,750,786,814,649,1265,140,651,683,863,211,396,593,335,822,141,656,720,443,398,826,341,326,600,124,764,802,730,451,665,915,573,51,150,603,403,1091,318,347,380,735,1190,381,171,938,956,1155,180,407,350,1064,352,1069,642,610,74,434,490,356,436,789,1156,279,916,752,1189,494,753,440,468,865,362,820,107,655,686,364,867,365,366,1186,238,756,690,499,564,473,445,565,694,830,125,372,932,53,762,503,329,982,698,373,475,664,542,731,632,132,1137,452,16,1029,316,510,806,317,43,285,479,319,547,635,808,578,428,549,323,740,776,741,1180,777,455,968,408,1204,191,302,1058,305,386,811]
		listIssueNumbers.each {
			println 'IssueNumber = ' + it
			def json = s.executeSearch(it)
			s.parseJson(json, "C:\\Users\\Rodrigo Andrade\\Desktop\\output.txt")
		}
	}
}
