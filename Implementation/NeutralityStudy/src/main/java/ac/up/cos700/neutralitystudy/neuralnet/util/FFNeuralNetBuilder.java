package ac.up.cos700.neutralitystudy.neuralnet.util;

import ac.up.cos700.neutralitystudy.function.IFunction;
import ac.up.cos700.neutralitystudy.function.util.NotAFunctionException;
import ac.up.cos700.neutralitystudy.neuralnet.*;

/**
 *
 * @author Abrie van Aardt
 */
public class FFNeuralNetBuilder {

    /**
     * Adds configuration information for an additional layer in the neural
     * network.
     * 
     * @param neuronCount
     * @param activationFunction
     * @return FFNeuralNetBuilder
     * @throws NotAFunctionException 
     */
    public FFNeuralNetBuilder addLayer(int neuronCount, Class activationFunction) throws NotAFunctionException {

        LayerConfig layerConfig = new LayerConfig();

        try {
            layerConfig.activationFunction = (IFunction) activationFunction.newInstance();
        }
        catch (InstantiationException e) {
            throw new NotAFunctionException();
        } catch (IllegalAccessException e){
            throw new NotAFunctionException();
        }
        
        if (config.layers.size() == 0) {//dealing with input layer
            layerConfig.weightCountPerNeuron = 0;
        } else {//dealing with hidden/output layer
            LayerConfig prevLayerConfig =
                    config.layers.get(config.layers.size()-1);
            //add 1 additional weight to act as the bias for this layer
            layerConfig.weightCountPerNeuron = prevLayerConfig.neuronCount + 1;                    
        }
        
        layerConfig.neuronCount = neuronCount;
        config.layers.add(layerConfig);

        return this;
    }
    
    /**
     * Instantiate a feed forward neural network with the specified
     * configuration.
     * 
     * @return IFFNeuralNet
     */
    public IFFNeuralNet build() {
        //clear configuration to reuse this bulder instance
        FFNeuralNetConfig tempConfig = config;
        config = new FFNeuralNetConfig();
        return new FFNeuralNet(tempConfig);
    }

    private FFNeuralNetConfig config = new FFNeuralNetConfig();
}
