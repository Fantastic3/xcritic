package fantastic3.xcritic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import fantastic3.xcritic.R;
import fantastic3.xcritic.activities.MusicActivity;
import fantastic3.xcritic.adapters.ListItemAdapter;
import fantastic3.xcritic.clients.v2.metacritic.MetacriticMusic;
import fantastic3.xcritic.interfaces.ListItemable;
import fantastic3.xcritic.models.Music;

/**
 * Created by jpodlech on 11/21/15.
 */
public class MusicsFragment extends Fragment {
    private View view;
    private ListView lvMusics;
    private List<ListItemable> items;

    public static MusicsFragment newInstance(Bundle args) {
        MusicsFragment instance = new MusicsFragment();
        if (args != null) instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_music, container, false);
        if (savedInstanceState == null) {
            setup();
        }
        MetacriticMusic.fetchBy(null, null, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                items = new ArrayList<ListItemable>();
                items.addAll(Music.fromJSONResults(json));
                ListItemAdapter adapter = new ListItemAdapter(getContext(), items);
                lvMusics.setAdapter(adapter);
                lvMusics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getActivity(), MusicActivity.class);
                        i.putExtra("music", (Music) items.get(position));
                        getActivity().startActivity(i);
                    }
                });
            }
        });
        return view;
    }

    private void setup() {
        setupViews();
    }

    private void setupViews() {
        lvMusics = (ListView) view.findViewById(R.id.lvMusic);
    }

    public void onQueryTextSubmit(String query) {

    }
}
