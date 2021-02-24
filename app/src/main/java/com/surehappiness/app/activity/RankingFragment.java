package com.surehappiness.app.activity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.surehappiness.app.R;
import com.surehappiness.app.dto.Rank;
import com.surehappiness.app.retrofit.RetrofitNetwork;
import com.surehappiness.app.retrofit.call.RankCall;
import com.surehappiness.app.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class RankingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_ranking,null);

        final NetworkInfo networkInfo = NetworkUtils.Companion.getNetworkInfo(getActivity());
        final RankCall rankCall = RetrofitNetwork.getInstance().getRankCall();

        final TextView myRank = view.findViewById(R.id.my_rank);

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_veiw);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final SharedPreferences userInfoPref = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        String userId = userInfoPref.getString("userId", null);

        if(networkInfo.isConnected()){
            Call<List<Rank>> call = rankCall.getRank(userId);
            call.enqueue(new Callback<List<Rank>>() {
                @Override
                public void onResponse(Call<List<Rank>> call, Response<List<Rank>> response) {
                    List<Rank> responseRankList = response.body();
                    if(responseRankList.get(0).getRanking() != null){
                        List<Rank> rankList = new ArrayList<>();
                        for(Rank rank : responseRankList){
                            if(rank.getMine() == 1){
                                myRank.setText(rank.getRanking() + " 위");
                            } else {
                                rankList.add(rank);
                            }
                        }

                        RankingListAdapter adapter = new RankingListAdapter(rankList);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Rank>> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder
                            .setMessage("서버 연결이 원활하지 않습니다.");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } else{
            AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
            ad.setMessage("네트워크 연결 상태를 확인해주세요.").show();
        }

        return view;
    }
}

class RankingListAdapter extends RecyclerView.Adapter<RankingListAdapter.ViewHolder>{

    private  List<Rank> mItems;

    public RankingListAdapter(List<Rank> items){
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item,parent,false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rank.setText(mItems.get(position).getRanking());
        holder.userId.setText(mItems.get(position).getUserId());
        holder.stampCount.setText(String.valueOf(mItems.get(position).getTotalStamp()));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank;
        TextView userId;
        TextView stampCount;

        ViewHolder(View view) {
            super(view);
            rank = view.findViewById(R.id.rank);
            userId = view.findViewById(R.id.user_id);
            stampCount = view.findViewById(R.id.stamp_count);

        }
    }
}
