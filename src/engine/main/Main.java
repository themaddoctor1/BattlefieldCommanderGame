/*
 * A SpontaneousSpawner will randomly create Units within the designated region.
 */
package engine.main;

import engine.game.spawning.*;
import engine.entities.*;
import engine.entities.units.helicopters.*;
import engine.entities.units.groundvehicles.*;
import engine.entities.units.soldiers.*;
import engine.entities.units.*;
import engine.entities.units.helicopters.AH64_Apache;
import engine.entities.units.testUnits.*;
import engine.game.*;
import engine.game.scenarios.TestScenario;
import engine.gui.*;
import engine.physics.*;
import engine.scripts.PauseScript;
import engine.scripts.RunScenarioScript;
import engine.world.*;
import java.util.ArrayList;

/**
 *
 * @author Christopher Hittner
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        (new RunScenarioScript(new TestScenario())).execute(args);
        
        while(true){
            GUI.getGUI().redraw();
        }
    }
    
}
