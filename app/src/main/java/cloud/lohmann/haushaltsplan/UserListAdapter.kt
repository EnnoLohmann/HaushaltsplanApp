package cloud.lohmann.haushaltsplan

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import cloud.lohmann.haushaltsplan.domain.HousekeepingTask
import cloud.lohmann.haushaltsplan.domain.TaskInterval

class UserListAdapter(private var activity: Activity, private var items: ArrayList<HousekeepingTask>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var imgChecked: ImageView? = null
        var imgInterval: ImageView? = null

        init {
            this.txtName = row?.findViewById<TextView>(R.id.txtName)
            this.imgChecked = row?.findViewById(R.id.imgChecked)
            this.imgInterval = row?.findViewById(R.id.imgAvatar)

        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.task_entry, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var userDto = items[position]
        viewHolder.txtName?.text = userDto.name
        if(userDto.finished)
            viewHolder.imgChecked?.visibility = View.VISIBLE;
        else
            viewHolder.imgChecked?.visibility = View.INVISIBLE;

        if(userDto.interval == TaskInterval.DAILY)
            viewHolder.imgInterval?.setImageResource(R.drawable.d)
        else if(userDto.interval == TaskInterval.WEEKLY)
        viewHolder.imgInterval?.setImageResource(R.drawable.w)
        else if(userDto.interval == TaskInterval.TWOWEEKLY)
            viewHolder.imgInterval?.setImageResource(R.drawable.t)
        else if(userDto.interval == TaskInterval.ZONE)
            viewHolder.imgInterval?.setImageResource(R.drawable.z)

        return view as View
    }

    override fun getItem(i: Int): HousekeepingTask {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}