/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.scripts;

/**
 *
 * @author Christopher Hittner
 */
public abstract class EncapsulatedScript {
    public final void execute(String[] params){
        script(params);
    }

    protected abstract void script(String[] params);
}
