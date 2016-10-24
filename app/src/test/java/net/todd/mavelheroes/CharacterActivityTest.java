package net.todd.mavelheroes;


import android.widget.TextView;

import net.todd.mavelheroes.net.todd.mavelheroes.data.CharacterThumbnail;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacter;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacterData;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacterResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.Arrays;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = LOLLIPOP, application = TestApp.class)
public class CharacterActivityTest {
    @Mock
    private MarvelService mockMarvelService;
    @Mock
    private Call<MarvelCharacterResponse> mockCall;
    @Captor
    private ArgumentCaptor<Callback<MarvelCharacterResponse>> responseArgumentCaptor;

    @Rule
    public DaggerMockRule<ApplicationComponent> daggerMockRule = new DaggerMockRule<>(ApplicationComponent.class, new ApplicationModule(((TestApp) RuntimeEnvironment.application)))
            .set(new DaggerMockRule.ComponentSetter<ApplicationComponent>() {
                @Override
                public void setComponent(ApplicationComponent applicationComponent) {
                    ((TestApp) RuntimeEnvironment.application).setComponent(applicationComponent);
                }
            });

    @Before
    public void setUp() {
        when(mockMarvelService.getCharacter(anyString())).thenReturn(mockCall);

    }

    @Test
    public void ensureDataIsPopulated() throws Exception {
        ActivityController<CharacterActivity> controller = Robolectric.buildActivity(CharacterActivity.class);
        CharacterActivity testObject = controller.create().start().resume().get();

        verify(mockCall).enqueue(responseArgumentCaptor.capture());
        Callback callback = responseArgumentCaptor.getValue();

        String characterId = "character id";
        String characterName = "character id";
        String bioData = "marvel hero bio";

        Response<MarvelCharacterResponse> response = Response.success(buildMarvelResponse(characterId, characterName, bioData));

        callback.onResponse(null, response);

        TextView textView = (TextView) testObject.findViewById(R.id.bio);

        assertEquals(bioData, textView.getText().toString());
    }

    private MarvelCharacterResponse buildMarvelResponse(String id, String name, String bio) {
        return new MarvelCharacterResponse(new MarvelCharacterData(Arrays.asList(new MarvelCharacter(id, name, bio, new CharacterThumbnail("", "")))));
    }
}