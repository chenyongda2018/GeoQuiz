//package example.chen.com.beatbox.viewmodel;
//
//import org.junit.Before;
//
//
//
//import org.junit.Test;
//import org.mockito.*;
//
//import example.chen.com.beatbox.model.BeatBox;
//import example.chen.com.beatbox.model.Sound;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//import static org.mockito.Mockito.*;
//
//
//public class SoundViewModelTest {
//
//    private BeatBox mBeatBox;
//    private Sound mSound;
//    private SoundViewModel mSubject;
//
//    @Before
//    public void setUp() throws Exception {
//        //创建虚拟对象
//        mBeatBox = mock(BeatBox.class);
//        mSound = new Sound("assetPath");
//        mSubject = mock(SoundViewModel.class);
//        mSubject.setSound(mSound);
//    }
//
//    @Test
//    public void exposesSoundNameAsTitle() {
//        assertThat(mSubject.getTitle(),is(mSound.getName()));
//    }
//}