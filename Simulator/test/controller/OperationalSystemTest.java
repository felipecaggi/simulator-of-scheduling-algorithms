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
     * Test of startSimulator method, of class OperationalSystem1.
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
        
        OperationalSystem1 os = new OperationalSystem1(cpu, memory);
        
        os.startSimulator("fcfs");
    }

//    /**
//     * Test of setProcessInformation method, of class OperationalSystem1.
//     */
//    @Test
//    public void testSetProcessInformation() {
//    }

}
