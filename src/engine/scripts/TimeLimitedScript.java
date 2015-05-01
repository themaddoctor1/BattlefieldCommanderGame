/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.scripts;

/**
 *
 * @author Christopher
 */
public abstract class TimeLimitedScript extends EncapsulatedScript{

    @Override
    protected final void script(String[] params) {
        String[] parms = new String[params.length-1];
        for(int i = 1; i < params.length; i++)
            parms[i-1] = params[i];
        cycle(Double.parseDouble(params[0]),parms);
    }
    
    protected abstract void cycle(double time, String[] params);
    
}
