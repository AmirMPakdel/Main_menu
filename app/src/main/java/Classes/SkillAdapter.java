package Classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coins.black.main_menu.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

/**
 * Created by Hooshang on 1/30/2018.
 */

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ViewHolder> {

    private List<SkillItem> skillItems;
    private Context context;

    public SkillAdapter(List<SkillItem> skillItems, Context context) {
        this.skillItems = skillItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.skills_folding_cell, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final SkillItem skillItem = skillItems.get(position);

        holder.textViewHead.setText(skillItem.getHead());
        holder.imageViewHead.setImageResource(skillItem.getImage());


    }

    @Override
    public int getItemCount() {

        return skillItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public ImageView imageViewHead;
        public LinearLayout linearLayout;
        public FoldingCell fc;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            imageViewHead = (ImageView) itemView.findViewById(R.id.imageViewHead);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            fc = (FoldingCell) itemView.findViewById(R.id.folding_cell);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fc != null)
                    {
                        fc.toggle(false);
                    }
                    else
                    {
                        throw new  RuntimeException("Kos Nago , Null mibashad");
                    }
                }
            });
        }



    }
}
