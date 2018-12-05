package controller;

import model.CPU;
import model.Memory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author felipecaggi
 */
public class OperationalSystemTest {
    
    public OperationalSystemTest() {
    }
    
    /**
     * Test of startSimulator method, of class OperationalSystem.
     */
    @Test
    public void testStartSimulator() {
        
        Memory memory = new Memory();
        memory.addProcess("A", 0, 40, "cpu");
        memory.addProcess("B", 0, 30, "cpu");
        memory.addProcess("C", 0, 5, 2, 30, "i/o");
        memory.addProcess("D", 0, 3, 2, 20, "i/o");
        
        CPU cpu = new CPU(2, 7);
        cpu.setMemory(memory);
        
        OperationalSystem os = new OperationalSystem(cpu, memory);
        
        os.startSimulator(1);
    }

//    /**
//     * Test of setProcessInformation method, of class OperationalSystem.
//     */
//    @Test
//    public void testSetProcessInformation() {
//    }

}
