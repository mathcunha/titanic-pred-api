package ml.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ml.pmml.Model;

@Controller
@RequestMapping("/evaluator")
public class EvaluatorController {
	
	private static final Model model = new Model();

	@RequestMapping(method=RequestMethod.GET)
    public @ResponseBody String evaluate(@RequestParam(value="Pclass", required=true) Float pclass,
    		@RequestParam(value="Age", required=true) Float age,
    		@RequestParam(value="SibSp", required=true) Float sibsp,
    		@RequestParam(value="Fare", required=true) Float fare,
    		@RequestParam(value="Female", required=true) Float female) {
        return model.evaluate(Model.getFeaturesMap(pclass, age, sibsp, fare, female)).toString();
    }
}
