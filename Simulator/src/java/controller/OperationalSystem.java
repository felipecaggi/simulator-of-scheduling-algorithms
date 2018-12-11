/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CPU;
import model.Memory;
import model.Process;
import model.ProcessLog;
import model.ShortTermScheduler;

/**
 *
 * @author felipecaggi
 */
public class OperationalSystem extends HttpServlet {
    
    private final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ", "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ"};

    private int ioRequestTime;
    private int ioProcessingTime;
    
    private Memory memory;
    private Memory memory2;
    private ShortTermScheduler scheduler;
    private CPU cpu;
    
    private int indexLog = 0;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jsp = null;

        if (request.getRequestURI().endsWith("/powerOn")) {
            powerOn(request);
            jsp = "/home.jsp";
        }
        
        else if (request.getRequestURI().endsWith("/algorithmSelected")) {
            algorithmSelected(request);
            jsp = "/home.jsp";
        }
        
        else if (request.getRequestURI().endsWith("/addProcess")) {
            addProcess(request);
            jsp = "/home.jsp";
        }
        
        else if (request.getRequestURI().endsWith("/removeProcess")) {
            removeProcess(request);
            jsp = "/home.jsp";
        }
        
        else if (request.getRequestURI().endsWith("/settings")) {
            settings(request);
            jsp = "/home.jsp";
        }
        
        else if (request.getRequestURI().endsWith("/saveSettings")) {
            saveSettings(request);
            jsp = "/home.jsp";
        }
        
        else if (request.getRequestURI().endsWith("/play")) {
            play(request);
            jsp = "/home.jsp";
        }
        
        else if (request.getRequestURI().endsWith("/next")) {
            next(request);
            jsp = "/home.jsp";
        }
        
        else if (request.getRequestURI().endsWith("/back")) {
            back(request);
            jsp = "/home.jsp";
        }

        request.getRequestDispatcher(jsp).forward(request, response);
    
    }
    
    public void powerOn(HttpServletRequest request) {
        
        request.getSession().setAttribute("saveSettings1", false);
        request.getSession().setAttribute("algorithmSelected", false);
        request.getSession().setAttribute("settings", false);
        request.getSession().setAttribute("powerOn", true);
        request.getSession().setAttribute("play", false);
        
        ioRequestTime = 0;
        ioProcessingTime = 0;
    
        memory = null;
        memory2 = null;
        scheduler = null;
        cpu = null;
    
        indexLog = 0;
        
        request.getSession().setAttribute("memory", null);
        request.getSession().setAttribute("cpu", null);
        request.getSession().setAttribute("saveSettings", false);
        request.getSession().setAttribute("statistics", false);
        
    }
    
    public void algorithmSelected(HttpServletRequest request) {
        
        String algorithm = request.getParameter("algorithm");
        
        request.getSession().setAttribute("algorithm", algorithm);
        
        request.getSession().setAttribute("saveSettings1", false);
        request.getSession().setAttribute("powerOn", false);
        request.getSession().setAttribute("settings", false);
        request.getSession().setAttribute("algorithmSelected", true);
        request.getSession().setAttribute("play", false);
        
    }
    
    public void addProcess(HttpServletRequest request) {

        if (memory == null)
            memory = new Memory();
        
        int executionTime = Integer.parseInt(request.getParameter("executionTime"));
        String processType = request.getParameter("process-type");
        int priority = 0;
        
        if (request.getSession().getAttribute("algorithm").equals("PRIORITY"))
            priority = Integer.parseInt(request.getParameter("priority"));
            
        int amount = memory.getQueueProcess().size();

        memory.addProcess(alphabet[amount], priority, executionTime, processType);
        
        request.getSession().setAttribute("memory", memory);
        request.getSession().setAttribute("memoryCreated", true);
        
    }
    
    public void removeProcess(HttpServletRequest request) {
        
        Memory memory = (Memory) request.getSession().getAttribute("memory");
        
        if (memory != null) {
            memory.removeProcess();
        }
        
        request.getSession().setAttribute("memory", memory);
        
        if (memory.getQueueProcess().isEmpty()){
            request.getSession().setAttribute("memory", null);
        }
            
        
    }
    
    public void settings(HttpServletRequest request) {
        
        request.getSession().setAttribute("saveSettings1", false);
        request.getSession().setAttribute("powerOn", false);
        request.getSession().setAttribute("algorithmSelected", false);
        request.getSession().setAttribute("settings", true);
        request.getSession().setAttribute("play", false);
    }
    
    public void saveSettings(HttpServletRequest request) {
        int sliceTime = 0;
        
        if (request.getSession().getAttribute("algorithm").equals("ROUNDROBIN"))
            sliceTime = Integer.parseInt(request.getParameter("sliceTime"));
        
        int contextSwitch = Integer.parseInt(request.getParameter("contextSwitch"));
        ioRequestTime = Integer.parseInt(request.getParameter("ioRequestTime"));
        ioProcessingTime = Integer.parseInt(request.getParameter("ioProcessingTime"));
        
        cpu = new CPU(contextSwitch, sliceTime);
        
        request.getSession().setAttribute("cpu", cpu);
        
        request.getSession().setAttribute("saveSettings", true);
        request.getSession().setAttribute("saveSettings1", true);
        
    }
    
    public void play(HttpServletRequest request) {
        
        memory2 = new Memory();
        
        for (Process p: memory.getQueueProcess()) {
            String name = p.getName();
            int time = p.getTime();
            String type = p.getType();
            int priority = p.getPriority();
            memory2.addProcess(name, ioRequestTime, ioProcessingTime, priority, time, type);
        }
        
        memory = memory2;
        memory.configureLogs();
        
        cpu.setMemory(memory);
        
        String algorithm = (String) request.getSession().getAttribute("algorithm");
        
        scheduler = new ShortTermScheduler(algorithm, cpu, memory);
                
        request.getSession().setAttribute("play", true);
        request.getSession().setAttribute("saveSettings1", false);
        request.getSession().setAttribute("powerOn", false);
        request.getSession().setAttribute("algorithmSelected", false);
        request.getSession().setAttribute("settings", false);
        
        request.getSession().setAttribute("log", scheduler.getLog(indexLog));
    }
    
    public void next(HttpServletRequest request) {
        if (indexLog < scheduler.getLog().size() -1){
            request.getSession().setAttribute("log", scheduler.getLog(++indexLog));
            memory.setQueueProcess(scheduler.getLog(indexLog).getMemoryReadyQueue());
            memory.setInputOutputRequest(scheduler.getLog(indexLog).getMemoryIOQueue());
            memory.setConcludedProcess(scheduler.getLog(indexLog).getMemoryConcludedQueue());
            memory.setProcessLogs(scheduler.getMemory().getProcessLogs());
            request.getSession().setAttribute("memory", memory);
        }
        
        if (indexLog == scheduler.getLog().size()-1) {
            request.getSession().setAttribute("play", false);
            request.getSession().setAttribute("statistics", true);
            
            statistics(request);
        }
    }
    
    public void back(HttpServletRequest request) {
        if (indexLog > 0){
            request.getSession().setAttribute("log", scheduler.getLog(--indexLog));
            memory.setQueueProcess(scheduler.getLog(indexLog).getMemoryReadyQueue());
            memory.setInputOutputRequest(scheduler.getLog(indexLog).getMemoryIOQueue());
            memory.setConcludedProcess(scheduler.getLog(indexLog).getMemoryConcludedQueue());
            memory.setProcessLogs(scheduler.getMemory().getProcessLogs());
            request.getSession().setAttribute("memory", memory);
        }
    }
    
    public void statistics(HttpServletRequest request) {
        
        int averageWaitTime = 0;
        
        for (ProcessLog pl: memory.getProcessLogs()) {
            averageWaitTime += pl.getMemoryTimeout();
        }
        
        averageWaitTime = averageWaitTime/memory.getProcessLogs().size();
        
        request.getSession().setAttribute("averageWaitTime", averageWaitTime);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
