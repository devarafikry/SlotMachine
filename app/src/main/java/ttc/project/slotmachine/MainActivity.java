package ttc.project.slotmachine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Thread spinThread1, spinThread2, spinThread3;

    ImageView image_1, image_2, image_3;
    Runnable runSpin1, runSpin2, runSpin3;

    int spinCounter = 0, n1=0, n2=0, n3=0;
    int timemillis = 200;

    TextView txt_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.image_1 = findViewById(R.id.image_1);
        this.image_2 = findViewById(R.id.image_2);
        this.image_3 = findViewById(R.id.image_3);

        this.txt_score = findViewById(R.id.txt_score);

        runSpin1 = new Runnable() // start actions in UI thread
        {
            int n = 0;
            @Override
            public void run()
            {
                int maxSize = datas().size();
                n1 = n;
                image_1.setImageResource(datas().get(n));
                n++;
                if(n==maxSize){
                    n = 0;
                }
            }
        };
        runSpin2 = new Runnable() // start actions in UI thread
        {
            int n = 0;
            @Override
            public void run()
            {
                int maxSize = datas().size();
                n2 = n;
                image_2.setImageResource(datas().get(n));
                n++;
                if(n==maxSize){
                    n = 0;
                }
            }
        };
        runSpin3 = new Runnable() // start actions in UI thread
        {
            int n = 0;
            @Override
            public void run()
            {
                int maxSize = datas().size();
                n3 = n;
                image_3.setImageResource(datas().get(n));
                n++;
                if(n==maxSize){
                    n = 0;
                }
            }
        };
    }

    private List<Integer> datas() {
        List<Integer> data = new ArrayList<>();
        data.add(R.drawable.p1);
        data.add(R.drawable.p2);
//        data.add(R.drawable.p3);
//        data.add(R.drawable.p4);
//        data.add(R.drawable.p5);
//        data.add(R.drawable.p6);
//        data.add(R.drawable.p7);
//        data.add(R.drawable.p8);
//        data.add(R.drawable.p9);
//        data.add(R.drawable.p10);
        data.add(R.drawable.p11);
//        data.add(R.drawable.p12);
//        data.add(R.drawable.p13);
        return data;
    }

    private void startSpinThread1(){
        spinThread1 = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (!Thread.interrupted()){
                    try
                    {
                        Thread.sleep(timemillis);
                        runOnUiThread(runSpin1);
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                        Log.d("THREAD","Interrupted");
                        Thread.currentThread().interrupt();
                    }
                }

            }
        });
        spinThread1.start();
    }

    private void startSpinThread2(){
        spinThread2 = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (!Thread.interrupted()){
                    try
                    {
                        Thread.sleep(timemillis);
                        runOnUiThread(runSpin2);
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                        Log.d("THREAD","Interrupted");
                        Thread.currentThread().interrupt();
                    }
                }

            }
        });
        spinThread2.start();
    }

    private void startSpinThread3(){
        spinThread3 = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (!Thread.interrupted()){
                    try
                    {
                        Thread.sleep(timemillis);
                        runOnUiThread(runSpin3);
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                        Log.d("THREAD","Interrupted");
                        Thread.currentThread().interrupt();
                    }
                }

            }
        });
        spinThread3.start();
    }

    public void spin(View view) {
        switch (spinCounter){
            case 0:
                resetGame();
                startSpinThread1();
                break;
            case 1:
                startSpinThread2();
                break;
            case 2:
                startSpinThread3();
                break;
            case 3:
                spinThread1.interrupt();
                break;
            case 4:
                spinThread2.interrupt();
                break;
            case 5:
                spinThread3.interrupt();
                setScore();
                break;
        }
        if (spinCounter == 5){
            spinCounter = 0;
        } else{
            spinCounter++;
        }
    }

    private void resetGame() {
        txt_score.setText("");
    }

    private void setScore() {
        String score;
        int posWheel1 = n1;
        int posWheel2 = n2;
        int posWheel3 = n3;

        if(posWheel1 == posWheel2){
            if(posWheel2 == posWheel3){
                score = "(3) Selamat anda mendapatkan JACKPOT !!";
            } else{
                score = "(2) Sedikit lagi, jangan menyerah. Ayo beli koin lagi!";
            }
        } else if (posWheel1 == posWheel3){
            if(posWheel1 == posWheel2){
                score = "(3) Selamat anda mendapatkan JACKPOT !!";
            } else{
                score = "(2) Sedikit lagi, jangan menyerah. Ayo beli koin lagi!";
            }
        } else if (posWheel2 == posWheel3){
            if(posWheel1 == posWheel3){
                score = "(3) Selamat anda mendapatkan JACKPOT !!";
            } else{
                score = "(2) Sedikit lagi, jangan menyerah. Ayo beli koin lagi!";
            }
        } else{
            score = "(1) Semua pasti pernah gagal, namun ini tidak menghentikanmu untuk beli koin lagi kan?";
        }

        txt_score.setText(score);
    }
}
