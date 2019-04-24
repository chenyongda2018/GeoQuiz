package example.chen.com.geoquiz.Model;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsSolved; //用户是否答对

    public Question(int mTextResId, boolean mAnswerTrue,boolean isSolved) {
        this.mTextResId = mTextResId;
        this.mAnswerTrue = mAnswerTrue;
        this.mIsSolved = isSolved;
    }

    public boolean isSolved() {
        return mIsSolved;
    }

    public void setSolved(boolean solved) {
        mIsSolved = solved;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
