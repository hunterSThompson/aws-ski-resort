package org.geobricks.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@Path("/hello")
public class SkiService {

    private QueueService _queueService = new QueueService();

    @POST
    @Path("/data")
    public Response postData() {
        return Response.status(200).build();
    }

    @GET
    @Path("/data")
    public Response getData() {
        long startExecutionTime = System.nanoTime();

        /* Pretend to add something to the DB for now. */
        int randomNum = ThreadLocalRandom.current().nextInt(100, 500);
        try {
            Thread.sleep(randomNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endExecutionTime = System.nanoTime();
        int exectutionTime = (int) (endExecutionTime - startExecutionTime);

        QueueMessage message = new QueueMessage(exectutionTime, 150, null, false);
        _queueService.sendMessage(message);


        /*
        int input = 0;
        int result = Utils.timedExecution( (val) -> {
            // do something that takes time
            return 15;
        }, input);
        */

        return Response.status(200).build();
    }

    /*
        TimedResult<SkiData> timedResult = Utils.timedExecution<DataRequest, SkiData>(
            (DataRequest d) -> {
                return dao.getSkiData(d);
            }
        );

        LogMessage message = new LogMessage(timedResult.time, null, overalltime, false);
     */

    /*
        To output: ErrorStr, ErrorBool, ExecutionTime, QueryTime
     */



}