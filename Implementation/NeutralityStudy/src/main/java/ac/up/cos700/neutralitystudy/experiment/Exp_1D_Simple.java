package ac.up.cos700.neutralitystudy.experiment;

import ac.up.cos700.neutralitystudy.data.Results;
import ac.up.cos700.neutralitystudy.function.problem.RealProblem;
import ac.up.cos700.neutralitystudy.neutralitymeasure.NeutralityMeasure;
import ac.up.cos700.neutralitystudy.sampling.ProgressiveRandomWalkSampler;
import ac.up.cos700.neutralitystudy.study.util.StudyConfig;
import ac.up.malan.phd.sampling.Walk;

/**
 *
 * @author Abrie van Aardt
 */
public class Exp_1D_Simple extends Experiment {

    public Exp_1D_Simple(StudyConfig _config, NeutralityMeasure _neutralityMeasure, RealProblem _problem) {
        super(_config, _neutralityMeasure, _problem);
        neutrality = new double[config.entries.get("simulations").intValue()];
        stepCount = config.entries.get("stepCount").intValue();
        stepRatio = config.entries.get("stepRatio");
        sampler = new ProgressiveRandomWalkSampler(problem, stepCount, stepRatio);
    }

    @Override
    protected void runSimulation(int currentSimulation) throws Exception {
        neutrality[currentSimulation - 1] = neutralityMeasure.measure(sampler.sample(), config.entries.get("epsilon"));
    }

    @Override
    protected void finalise() throws Exception {
        Results.writeToFile(path, name + "_Neutrality", neutrality);

        //graph of problem
//        Results.newGraph(this, path, problem.getName(), "x", "f(x)", null, 2);
//        Results.addPlot(this, null, problem);
//        Results.plot(this);
        //graph of problem - showing sample           
        Results.newGraph(this, path, problem.getName() + " Sampled", "x", "f(x)", null, 2);
        Results.addPlot(this, problem.getName(), problem);

        Walk[] walks = sampler.sample();
        for (int i = 0; i < walks.length; i++) {
            Results.addPlot(this, "Walk " + (i + 1), walks[i].getPoints(), walks[i].getPointsFitness(), "linespoints");
        }

        Results.plot(this);

    }

    protected final double[] neutrality;
    protected final int stepCount;
    protected final double stepRatio;

}
