package utils;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * This class is a constant class with the definition of language supported list differentiating between the current iso standard (ISO 3) and the old one (ISO 2) which is the only one supported by the external library thymeleaf.
 * @author Marco Petri
 */
public class Const {
	// there can't be instantiation of this class
	private Const() {}
	
	// project version
	public static final long EJBVersion = 1L;
	public static final long WebVersion = 1L;
	public static final long ExceptionVersion = 1L;

	// accepted languages list
	public static final String defaultLanguage = "eng";
	public static final String defaultCountry = "US";
	public static final List<String> acceptedLangTags = List.of("ita","eng");
	public static final List<String> acceptedOldIsoLangTags = List.of("it","en");
	public static final Map<String,String> oldIsoLangTagsToNew = Map.of("it","ita","en","eng");
	public static final Map<String,String> newIsoLangTagsToOld = Map.of("ita","it","eng","en");
	public static final Map<String,String> isoTagToCountry = Map.of("ita","IT","eng","US");

	// default properties files position
	public static final String propertiesBaseName = "resources.QuestionnaireOfTheDay";

	// error messages before having instantiated the correct lang
	public static final String unavailableException = "Can't find database driver";
	public static final String sqlException = "Can't load database";
	
	// db values
	public static final int answersMaxLength = 50;
}
