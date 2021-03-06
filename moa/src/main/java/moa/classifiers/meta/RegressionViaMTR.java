package moa.classifiers.meta;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.predictions.Prediction;
import com.yahoo.labs.samoa.instances.predictions.RegressionPrediction;

import moa.classifiers.AbstractRegressor;
import moa.core.Measurement;
import moa.learners.MultiTargetRegressor;
import moa.learners.Regressor;
import moa.options.ClassOption;

public class RegressionViaMTR extends AbstractRegressor implements Regressor {

	public MultiTargetRegressor mtr;

	public ClassOption regressorOption = new ClassOption("MTregessor", 'c', "Multi-target regressor to be used as a single target regressor.", MultiTargetRegressor.class, "moa.classifiers.mtr.ISOUPTree");

	@Override
	public boolean isRandomizable() {
		return true;
	}

	@Override
	public void getModelDescription(StringBuilder out, int indent) {
		out.append("Meta single-target regressor which uses a multi-target regressor with a single target.");
	}

	@Override
	public Prediction getPredictionForInstance(Instance instance) {
		Prediction p = mtr.getPredictionForInstance(instance);
		return new RegressionPrediction(p.getPrediction(0));
	}

	@Override
	public void trainOnInstanceImpl(Instance inst) {
		mtr.trainOnInstance(inst);
	}

	@Override
	public void resetLearningImpl() {
		if (this.mtr == null)
			this.mtr = (MultiTargetRegressor) getPreparedClassOption(this.regressorOption);
		this.mtr.resetLearning();
	}

	@Override
	protected Measurement[] getModelMeasurementsImpl() {
		return this.mtr.getModelMeasurements();
	}



}
