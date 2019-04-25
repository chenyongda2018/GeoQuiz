package example.chen.com.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import example.chen.com.geoquiz.Model.Question;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 111;

    private boolean mIsCheat;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private TextView mQuestionView;
    private ImageButton mNextButton;
    private ImageButton mPreButton;


    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mQuestionView = (TextView) findViewById(R.id.question_view);
        //取出上次保存的数据
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }
        //更新问题UI
        updateQuestionView();
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(this);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(this);
        mPreButton = (ImageButton) findViewById(R.id.pre_button);
        mPreButton.setOnClickListener(this);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(this);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.true_button:
                checkAnswer(true);
                break;
            case R.id.false_button:
                checkAnswer(false);
                break;
            case R.id.next_button:
                nextQuestionView();
                break;
            case R.id.question_view:
                nextQuestionView();
                break;
            case R.id.pre_button:
                preQuestionView();
                break;
            case R.id.cheat_button:
                //进入查看答案页面
                boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                Intent cheatIntent = CheatActivity.newIntent(this, answerIsTrue);
                startActivityForResult(cheatIntent, REQUEST_CODE_CHEAT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheat = CheatActivity.wasAnswerIsShown(data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }


    //保存上次活动的数据
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    //更新问题UI
    private void updateQuestionView() {
        mQuestionView.setText(mQuestionsBank[mCurrentIndex].getTextResId());
    }


    //根据用户点击的答案(true/false)弹出对应的消息
    public void checkAnswer(boolean userAnswer) {
        boolean questionAnswer = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        if (userAnswer == questionAnswer) {
            mQuestionsBank[mCurrentIndex].setSolved(true);
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        updateButtonByIndex();
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }


    //页面跳转到后一个问题
    public void nextQuestionView() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
        updateQuestionView();
        updateButtonByIndex();
    }

    //页面跳转到前一个问题
    public void preQuestionView() {
        if (mCurrentIndex > 0) {
            mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
        } else {
            mCurrentIndex = mQuestionsBank.length - 1;
        }

        updateQuestionView();
        updateButtonByIndex();
    }

    private void updateButtonByIndex() {
        boolean isSolved = mQuestionsBank[mCurrentIndex].isSolved();
        if (isSolved) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }


}
