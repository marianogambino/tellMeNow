package ag.it.solution.notifystudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ag.it.solution.notifystudent.R;
import ag.it.solution.notifystudent.itemList.ContactItem;

/**
 * Created by mariano on 03/08/2016.
 */
public class ListContactAdapter extends ArrayAdapter<ContactItem> {

    public ListContactAdapter(Context context, List<ContactItem> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_user , parent, false);

//            TextView username = (TextView) convertView.findViewById(R.id.username);
//            ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ContactItem item = getItem(position);
        //Glide.with(getContext()).load(item.getImage()).into(avatar);
        holder.username.setText( item.getUsername() );

        return convertView;
    }

    static class ViewHolder {
        ImageView avatar;
        TextView username;
    }

}
