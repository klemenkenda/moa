/*
 *    OzaBagML.java
 *    Copyright (C) 2012 University of Waikato, Hamilton, New Zealand
 * 
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program. If not, see <http://www.gnu.org/licenses/>.
 *    
 */
package moa.classifiers.mlc.meta;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.MultiLabelInstance;
import com.yahoo.labs.samoa.instances.predictions.MultiTargetRegressionPrediction;
import com.yahoo.labs.samoa.instances.predictions.Prediction;

import moa.classifiers.meta.AbstractOzaBag;
import moa.core.DoubleVector;
import moa.learners.MultiLabelClassifier;

/**
 * OzaBag for Multi-label data.
 * 
 * @author Jesse Read
 * @version $Revision: 1 $
 */
public class OzaBagMultiLabelClassification extends AbstractOzaBag<MultiLabelClassifier> implements MultiLabelClassifier {

	private static final long serialVersionUID = 1L;

	public OzaBagMultiLabelClassification() {
    	super(MultiLabelClassifier.class, "multilabel.trees.ISOUPTree");
    }

	@Override
	public Prediction getPredictionForInstance(MultiLabelInstance inst) {
		return this.getPredictionForInstance((Instance) inst);
	}

	@Override
	public Prediction combinePredictions(Prediction[] predictions) {
		DoubleVector sums = new DoubleVector();
		for (Prediction p : predictions) {
			sums.addValues(p.asDoubleVector());
		}
		sums.scaleValues(1 / sums.numValues());
		return new MultiTargetRegressionPrediction(sums);
	}

	@Override
	public void trainOnInstanceImpl(MultiLabelInstance inst) {
		this.trainOnInstanceImpl((Instance) inst);
		
	}
    
}

