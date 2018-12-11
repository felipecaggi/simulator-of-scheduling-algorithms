<%-- 
    Document   : home
    Created on : Dec 5, 2018, 11:27:31 AM
    Author     : felipecaggi
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/computer.css"/>
        <title>JSP Page</title>
    </head>
    <body id="poweron-page">
        <div class="outer">
            <div class="middle">
                <div class="computer">
                    <div class="monitor">
                        <div class="screen">
                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->                            
                            <c:if test="${powerOn == true}">
                                <div class="algorithms">
                                    <div class="row">
                                        <form method="POST" action="${pageContext.request.contextPath}/algorithmSelected">
                                            <input type="hidden" id="algorithm" name="algorithm" value="FCFS">
                                            <input type="submit" class="fcfs" value="FCFS">
                                        </form>
                                        <form method="POST" action="${pageContext.request.contextPath}/algorithmSelected">
                                            <input type="hidden" id="algorithm" name="algorithm" value="SJF">
                                            <input type="submit" class="sjf" value="SJF">
                                        </form>
                                    </div>
                                    <div class="row">
                                        <form method="POST" action="${pageContext.request.contextPath}/algorithmSelected">
                                            <input type="hidden" id="algorithm" name="algorithm" value="PRIORITY">
                                            <input type="submit" class="priority" value="PRIORITY">
                                        </form>
                                        <form method="POST" action="${pageContext.request.contextPath}/algorithmSelected">
                                            <input type="hidden" id="algorithm" name="algorithm" value="ROUNDROBIN">
                                            <input type="submit" class="roundRobin" value="ROUND ROBIN">
                                        </form>
                                    </div>
                                </div>
                            </c:if>
                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->
                            <c:if test="${algorithmSelected == true}">
                                <div class="form">

                                    <!-- Formulário de processo -->
                                    <form class="form-add-process" method="POST" action="${pageContext.request.contextPath}/addProcess">

                                        <div class="executionTime">
                                            <label for="executionTime">Execution Time</label><br>
                                            <input type="text" name="executionTime" id="executionTime" required><br>
                                        </div>

                                        <div class="process-type">
                                            <input type="radio" name="process-type" value="cpu" checked><label for="cpu">CPU Oriented</label><br>
                                            <input type="radio" name="process-type" value="io"><label for="io">I/O Oriented</label><br>    
                                        </div>
                                        
                                        <c:if test="${algorithm == 'PRIORITY'}">
                                            <div class="priority">
                                                <label for="priority">Priority</label><br>
                                                <input type="text" name="priority" id="priority" required><br>
                                            </div>
                                        </c:if>
                                        
                                        <input type="submit" value="Add Process">

                                    </form>

                                    <form class="form-remove-process" method="POST" action="${pageContext.request.contextPath}/removeProcess">

                                        <input type="submit" value="Remove Process">

                                    </form>
                                </div>
                            </c:if>
                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->                            
                            <c:if test="${settings == true}">
                                <div class="form">

                                    <!-- Formulário de configurações -->
                                    <form class="form-settings" method="POST" action="${pageContext.request.contextPath}/saveSettings">
                                        
                                        <c:if test="${algorithm == 'ROUNDROBIN'}">
                                            <div class="slice-time">
                                                <label for="sliceTime">Slice Time</label><br>
                                                <input type="text" name="sliceTime" id="sliceTime" required value="5"><br>
                                            </div>
                                        </c:if>

                                        <div class="context-switch">
                                            <label for="contextSwitch">Context Switch</label><br>
                                            <input type="text" name="contextSwitch" id="contextSwitch" required value="1"><br>
                                        </div> 

                                        <div class="io-request-time">
                                            <label for="ioRequestTime">I/O Request Time</label><br>
                                            <input type="text" name="ioRequestTime" id="ioRequestTime" required value="7"><br>
                                        </div>

                                        <div class="io-processing-time">
                                            <label for="ioProcessingTime">I/O Processing Time</label><br>
                                            <input type="text" name="ioProcessingTime" id="ioProcessingTime" required value="4"><br>
                                        </div>

                                        <input type="submit" value="Save settings">

                                    </form>

                                    <c:if test="${saveSettings1  == true}">
                                        <div class="sucess">
                                            <p>Settings saved with success</p>
                                        </div>
                                    </c:if>

                                </div>
                            </c:if>
                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->
                            <c:if test="${play == true}">
                                <div class="informations-container">
                                    <div class="process-information">
                                        <table class="statistics-table">
                                            <tr class="collumns-table">
                                                <th class="name-collumn">Process name</th>
                                            </tr>
                                                
                                            <tr class="process">

                                                <td class="name-collumn">
                                                    <div class="name">
                                                        <p><c:out value="CPU Cycle: ${log.cpuCycle}"/></p>
                                                    </div>
                                                </td>

                                            </tr>
                                            

                                            <c:forEach var="message" items="${log.message}">


                                                <%-- Each table row represent a process --%>
                                                <tr class="process">

                                                    <td class="name-collumn">
                                                        <div class="name">
                                                            <p><c:out value="${message}"/></p>
                                                        </div>
                                                    </td>

                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </div>
                                <%--
                                <div class="informations-container">
                                    <div class="cpu-information">
                                        <c:forEach var="mensage" items="${log.mensage}">
                                            <p>${mensage}</p>
                                        </c:forEach>
                                    </div>
                                </div>            
--%>
                                <div class="docbar">

                                    <a class="step back" href="${pageContext.request.contextPath}/back">
                                        <div class="step4">
                                            <p>BACK</p>
                                        </div>
                                    </a>
                                    <a class="step next" href="${pageContext.request.contextPath}/next">
                                        <div class="step4">
                                            <p>NEXT</p>
                                        </div>
                                    </a>
                                    <a class="step play" href="${pageContext.request.contextPath}/play">
                                        <div class="step4">
                                            <p>PLAY</p>
                                        </div>
                                    </a>   
                                </div>

                                <div class="test">
                                    <c:forEach var="generalLog" items="${cpuConclude.generalLog}">
                                        <c:forEach var="cycleLog" items="${generalLog.cycleLog}">
                                            <p><c:out value="${cycleLog}"/></p>
                                        </c:forEach>
                                    </c:forEach>
                                </div>
                            </c:if>
                            
                            <c:if test="${statistics == true}">
                                <div class="informations-container">
                                    <div class="process-information">
                                        <table class="statistics-table">
                                            <tr class="collumns-table">
                                                <th class="name-collumn">Process name</th>
                                                <th class="time-collumn">Memory timeout</th>
                                                <th class="time-collumn">CPU input cycle</th>
                                                <th class="time-collumn">CPU output cycle</th>
                                                <th class="time-collumn">Turnaround time</th>
                                                
                                            </tr>

                                            <c:forEach var="process" items="${memory.processLogs}">

                                                <%-- Each table row represent a process --%>
                                                <tr class="process">

                                                    <td class="name-collumn">
                                                        <div class="name">
                                                            <p><c:out value="${process.name}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="time-collumn">
                                                        <div class="time">
                                                            <p><c:out value="${process.memoryTimeout}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="time-collumn">
                                                        <div class="time">
                                                            <p><c:out value="${process.cpuInputCycle}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="time-collumn">
                                                        <div class="time">
                                                            <p><c:out value="${process.cpuOutputCycle}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="time-collumn">
                                                        <div class="time">
                                                            <p><c:out value="${process.turnaroundTime}"/></p>
                                                        </div>
                                                    </td>


                                                </tr>
                                            </c:forEach>
                                                <tr class="process">
                                                    <td class="name-collumn">
                                                        <div>
                                                            <p><c:out value="Average memory timeout"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="time-collumn">
                                                        <div>
                                                            <p><c:out value="${averageMemoryTimeout}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="name-collumn"></td>
                                                    <td class="name-collumn"></td>
                                                    <td class="name-collumn"></td>
                                                </tr>
                                                
                                                <tr class="process">
                                                    <td class="name-collumn">
                                                        <div>
                                                            <p><c:out value="Average CPU Response Time"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="name-collumn"></td>
                                                    <td class="time-collumn">
                                                        <div>
                                                            <p><c:out value="${averageCpuResponseTime}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="name-collumn"></td>
                                                    <td class="name-collumn"></td>
                                                </tr>

                                                <tr class="process">
                                                    <td class="name-collumn">
                                                        <div>
                                                            <p><c:out value="CPU throughput"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="name-collumn"></td>
                                                    <td class="name-collumn"></td>
                                                    <td class="time-collumn">
                                                        <div>
                                                            <p><c:out value="${cpuTroughput}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="name-collumn"></td>
                                                </tr>
                                                
                                                <tr class="process">
                                                    <td class="name-collumn">
                                                        <div>
                                                            <p><c:out value="Average Turnaround Time"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="name-collumn"></td>
                                                    <td class="name-collumn"></td>
                                                    <td class="name-collumn"></td>
                                                    <td class="time-collumn">
                                                        <div>
                                                            <p><c:out value="${averageTurnaroundTime}"/></p>
                                                        </div>
                                                    </td>
                                                </tr> 
                                                
                                                                                          
                                        </table>
                                    </div>
                                </div>

                            </c:if>

                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->
                            <!-- =========================================================================================================================================================================== -->                            
                            <c:if test="${play == false && (statistics == false || statistics == null)}">
                                <div class="docbar">
                                    <a class="step" href="${pageContext.request.contextPath}/powerOn">
                                        <div class="step1">
                                            <p>algorithm select</p>
                                        </div>
                                    </a>
                                    <a class="step" href="${pageContext.request.contextPath}/algorithmSelected">
                                        <div class="step2">
                                            <p>add process</p>
                                        </div>
                                    </a>
                                    <a class="step" href="${pageContext.request.contextPath}/settings">
                                        <div class="step3">
                                            <p>settings</p>
                                        </div>
                                    </a>
                                    <c:if test="${algorithm != null && memoryCreated != false && saveSettings != false}">
                                        <a class="step play" href="${pageContext.request.contextPath}/play">
                                            <div class="step4">
                                                <p>PLAY</p>
                                            </div>
                                        </a>    
                                    </c:if>
                                </div>
                            </c:if>
                        </div>
                        <div class="foot">
                            <div class="foot-p1"></div>
                            <div class="foot-p2"></div>
                            <div class="foot-p3"></div>
                        </div>
                    </div>



                    <div class="computer-gabinet">
                        <c:if test="${memory != null}">

                            <div class="memory">
                                <div class="indicate"></div>
                                <div class="memory-chip">
                                    <div class="chip"></div>
                                    <div class="chip"></div>
                                    <div class="chip"></div>
                                    <div class="chip"></div>
                                    <div class="chip"></div>
                                    <div class="chip"></div>
                                </div>
                                <div class="pin-connection1"></div>
                                <div class="pin-connection2"></div>
                                
                                <div class="queues">
                                    
                                    <div class="ready-queue queue">
                                        <p class="queue-title">Ready queue process</p>
                                        <table class="ready-queue-table">
                                            <tr class="collumns-table">
                                                <th class="name-collumn">Name</th>
                                                <th class="time-collumn">Time</th>
                                                <th class="type-collumn">Type</th>
                                            </tr>

                                            <c:forEach var="process" items="${memory.queueProcess}">

                                                <%-- Each table row represent a process --%>
                                                <tr class="process">

                                                    <td class="name-collumn">
                                                        <div class="name">
                                                            <p><c:out value="${process.name}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="time-collumn">
                                                        <div class="time">
                                                            <p><c:out value="${process.time}"/></p>
                                                        </div>
                                                    </td>

                                                    <%-- Coluna do valor total (quantidade de acomodações * valor da acomodação) --%>
                                                    <td class="type-collumn">
                                                        <div class="type">
                                                            <p><c:out value="${process.type}"/></p>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>

                                    </div>
                                    
                                    <div class="io-queue queue">
                                        <p class="queue-title">I/O queue process</p>
                                        <table class="ready-queue-table">
                                            <tr class="collumns-table">
                                                <th class="name-collumn">Name</th>
                                                <th class="time-collumn">TimeWait</th>
                                                <th class="type-collumn">Type</th>
                                            </tr>

                                            <c:forEach var="process" items="${memory.inputOutputRequest}">

                                                <%-- Each table row represent a process --%>
                                                <tr class="process">

                                                    <td class="name-collumn">
                                                        <div class="name">
                                                            <p><c:out value="${process.name}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="time-collumn">
                                                        <div class="time">
                                                            <p><c:out value="${process.timeWaitIO}"/></p>
                                                        </div>
                                                    </td>

                                                    <%-- Coluna do valor total (quantidade de acomodações * valor da acomodação) --%>
                                                    <td class="type-collumn">
                                                        <div class="type">
                                                            <p><c:out value="${process.type}"/></p>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>

                                    </div>
                                    
                                    <div class="concluded-queue queue">
                                        <p class="queue-title">Concluded queue process</p>
                                        <table class="ready-queue-table">
                                            <tr class="collumns-table">
                                                <th class="name-collumn">Name</th>
                                                <th class="time-collumn">Time</th>
                                                <th class="type-collumn">Type</th>
                                            </tr>

                                            <c:forEach var="process" items="${memory.concludedProcess}">

                                                <%-- Each table row represent a process --%>
                                                <tr class="process">

                                                    <td class="name-collumn">
                                                        <div class="name">
                                                            <p><c:out value="${process.name}"/></p>
                                                        </div>
                                                    </td>
                                                    <td class="time-collumn">
                                                        <div class="time">
                                                            <p><c:out value="${process.time}"/></p>
                                                        </div>
                                                    </td>

                                                    <%-- Coluna do valor total (quantidade de acomodações * valor da acomodação) --%>
                                                    <td class="type-collumn">
                                                        <div class="type">
                                                            <p><c:out value="${process.type}"/></p>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>

                                    </div>
                                    
                                </div>
                                
                            </div>
                        </c:if>
                        
                        <c:if test="${saveSettings == true}">
                        <div class="cpu">
                            <div class="cpu-chip">
                                <div class="cpu-process">
                                    <p>
                                        ${log.processName}
                                    </p>
                                </div>
                                <div class="connector-group group1">
                                    <div class="connector1"></div>
                                    <div class="connector1"></div>
                                    <div class="connector1"></div>
                                </div>
                                <div class="connector-group group2">
                                    <div class="connector2"></div>
                                    <div class="connector2"></div>
                                    <div class="connector2"></div>
                                </div>
                                <div class="connector-group group3">
                                    <div class="connector3"></div>
                                    <div class="connector3"></div>
                                    <div class="connector3"></div>
                                </div>
                                <div class="connector-group group4">
                                    <div class="connector4"></div>
                                    <div class="connector4"></div>
                                    <div class="connector4"></div>
                                </div>
                            </div>


                            <div class="cpu-settings">
                                <form class="process-form">

                                        <div id="process-cycle" class="process-cycle">                      
                                            <input type="text" id="nome" value="${log.processCycle}" disabled>
                                            <label class="input-wrap-label" for="nome">Process Cycle</label>
                                        </div>
                                        <div id="time-left" class="time-left">
                                            <input type="text" id="nome" value="${log.processTime}" disabled>
                                            <label class="input-wrap-label" for="nome">Time Left</label>
                                        </div>

                                </form>

                                <form class="cpu-form">

                                    <div id="input-name" class="input-wrap">
                                        <label class="input-wrap-label" for="nome">Context Switch</label>
                                        <input type="text" id="nome" value="${log.cpuContextSwitch}" disabled>
                                    </div>

                                    <c:if test="${algorithm == 'ROUNDROBIN'}">

                                        <div id="input-cpf" class="input-wrap">
                                            <label class="input-wrap-label" for="cpf">Slice</label>
                                            <input type="text" id="cpf" value="${log.cpuSlice}" disabled>
                                        </div>
                                    </c:if>

                                    <div id="input-phone" class="input-wrap">
                                        <label class="input-wrap-label" for="telefone">State</label>
                                        <input type="text" id="telefone" value="${log.cpuState}" disabled>
                                    </div>

                                    <div id="input-email" class="input-wrap">
                                        <label class="input-wrap-label" for="email">CPU Cycle</label>
                                        <input type="text" id="email" value="${log.cpuCycle}" disabled>
                                    </div>

                                </form>
                            </div>    
                        </div>
                        </c:if>
                            
                        <a class="power-button" href="${pageContext.request.contextPath}/powerOn"></a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
