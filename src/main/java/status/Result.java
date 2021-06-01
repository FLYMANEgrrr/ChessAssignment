package status;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.util.Duration;

/**
 *  provide support for game result.
 *  start() function is start to counting result;
 *  for each moves we need to add moves steps, is using addMove() function;
 *  end() function it's to end of counting result;
 */
public class Result {
    private final LongProperty time= new SimpleLongProperty();
    private final IntegerProperty countMove=new SimpleIntegerProperty();
    private final Timeline clock;

    /**
     * create new result
     */
    public Result(){
        clock = new Timeline( new KeyFrame(Duration.ZERO,
                t -> time.set(time.get()+1000)),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * init start function
     * the countMove setting 0,
     * time also 0,
     * clock is start
     */
    public void start(){
        countMove.set(0);
        time.set(0);
        clock.play();
    }

    /**
     * to stop the timer
     */
    public void end(){
        clock.pause();
    }

    /**
     * add 1 move into countMove;
     */
    public void addMove(){
        countMove.set(countMove.get()+1);
    }

    /**
     * return the result base on the cost moves and the cost times.
     * @return result; after calculated, we got the result;
     */
    public int getResult(){
        return calculate(countMove.get(),time.get());
    }

    public static int calculate(int move, long time){
        return 2000-((int) time>>7) - move*50;
    }

    /**
     * return time property
     * @return
     */
    public LongProperty timeProperty(){
        return time;
    }

    /**
     * return cost of moves step times property
     * @return countMove property
     */
    public IntegerProperty moveProperty(){
        return countMove;
    }

}
