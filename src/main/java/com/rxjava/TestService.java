package com.rxjava;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;





import rx.Observable;
import rx.schedulers.Schedulers;

@Service
public class TestService {
    private Logger logger = LoggerFactory.getLogger(TestService.class);
    
    private final ExecutorService customObservableExecutor = Executors.newFixedThreadPool(10);

    public Observable<String> hello() {
      return Observable.just("Hello World - Dara");
    }
    
  public Observable<String> getAMessageObs() {
    return Observable.<String>create(s -> {
      TimeZone tz = TimeZone.getTimeZone("UTC");
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
      df.setTimeZone(tz);
      
      logger.info("Start: Executing slow task in Service 1");

      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        // Ignore
      }

      s.onNext("data : " + df.format(Calendar.getInstance().getTime()));
      logger.info("End: Executing slow task in Service 1");
      s.onCompleted();
    }).subscribeOn(Schedulers.from(customObservableExecutor));
  }
}
