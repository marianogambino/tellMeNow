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
import ag.it.solution.notifystudent.itemList.MessageItem;

/**
 * Created by mariano on 07/09/2016.
 */
public class MessageAdapter extends ArrayAdapter<MessageItem>{

    public MessageAdapter(Context context, List<MessageItem> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;
        MessageItem item = getItem(position);

//        if(convertView == null){
            holder = new ViewHolder();

            if(item.getAction().equalsIgnoreCase("E")){
                convertView = inflater.inflate(R.layout.list_item_message_right , parent, false);
                //holder.messageFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
                //holder.messageFrom.setText( "" );
                holder.message = (TextView) convertView.findViewById(R.id.txtMsgRight);
            }else{
                convertView = inflater.inflate(R.layout.list_item_message_left , parent, false);
               // holder.messageFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
                //holder.messageFrom.setText( item.getUsername() );
                holder.message = (TextView) convertView.findViewById(R.id.txtMsgLeft);
            }

            holder.message.setText( item.getMessage() );
            convertView.setTag(holder);

       // }else{
        //    holder = (ViewHolder) convertView.getTag();
        //}



        return convertView;
    }

    static class ViewHolder {
        TextView message;
        TextView messageFrom;
        TextView timestamp;
        TextView statusTxt;
        String status;
    }
}
