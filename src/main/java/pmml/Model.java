package ml.pmml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.xml.sax.SAXException;

public class Model {
	
	private ModelEvaluator<?> modelEvaluator;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public Model()  {
		try {
			modelEvaluator = loadEvaluator();
		} catch (SAXException | JAXBException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public Map<FieldName, ?> evaluate(Map<FieldName, Float> arguments){
		return modelEvaluator.evaluate(arguments);
	}
	
	public static PMML load(InputStream is) throws SAXException, JAXBException {
		return org.jpmml.model.PMMLUtil.unmarshal(is);
	}

	public static ModelEvaluator<?> loadEvaluator() throws SAXException, JAXBException {
		PMML pmml = load(Model.class.getResourceAsStream("/pipeline_named.pmml"));
		ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
		return modelEvaluatorFactory.newModelEvaluator(pmml);
	}

	public static Map<FieldName, Float> getFeaturesMap(float pclass, float age, float sibsp, float fare, float female) {
		Map<FieldName, Float> mapa = new HashMap<>();
		mapa.put(new FieldName("Pclass"), new Float(pclass));
		mapa.put(new FieldName("Age"), new Float(age));
		mapa.put(new FieldName("SibSp"), new Float(sibsp));
		mapa.put(new FieldName("Fare"), new Float(fare));
		mapa.put(new FieldName("Female"), new Float(female));
		return mapa;
	}

	public static void main(String[] args) throws SAXException, JAXBException {
		ModelEvaluator<?> modelEvaluator = loadEvaluator();

		System.out.println(modelEvaluator.evaluate(getFeaturesMap(3f, 34.5f, 0f, 14.4542f, 0f)));
		System.out.println(modelEvaluator.evaluate(getFeaturesMap(3f, 47f, 1f, 14.4542f, 1f)));
		System.out.println(modelEvaluator.evaluate(getFeaturesMap(1f, 23f, 1f, 82.2667f, 1f)));
//Pclass=3&Age=34.5&SibSp=0&Fare=14.4542&Female=0
	}
}
