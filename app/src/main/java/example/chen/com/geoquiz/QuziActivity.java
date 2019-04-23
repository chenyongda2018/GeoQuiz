package example.chen.com.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuziActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionView;
    private ImageButton mNextButton;
    private ImageButton mPreButton;


    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mQuestionView = (TextView) findViewById(R.id.question_view);
        updateQuestionView();
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreButton =(ImageButton) findViewById(R.id.pre_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton = (Button) findViewById(R.id.true_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);

            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestionView();
            }
        });

        mQuestionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestionView();
            }
        });

        mPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preQuestionView();
            }
        });

    }

    //更新问题UI
    private void updateQuestionView() {
        mQuestionView.setText(mQuestionsBank[mCurrentIndex].getTextResId());
    }

    public void checkAnswer(boolean userAnswer) {
        boolean questionAnswer = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        if (userAnswer == questionAnswer) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    public void nextQuestionView() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
        updateQuestionView();
    }

    public void preQuestionView() {
        if (mCurrentIndex > 0) {
            mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
        } else {
            mCurrentIndex = mQuestionsBank.length-1;
        }

        updateQuestionView();
    }
}
