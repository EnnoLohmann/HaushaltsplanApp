package cloud.lohmann.haushaltsplan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import cloud.lohmann.haushaltsplan.domain.HousekeepingTask
import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    var listView: ListView? = null
    var taskList: ArrayList<HousekeepingTask>? = ArrayList();
    var adapter = this!!.taskList?.let { UserListAdapter(this, it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            generateData()
        }
        listView = findViewById(R.id.listview)
        listView?.adapter = adapter
        adapter?.notifyDataSetChanged()

        listView?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->

            var item: HousekeepingTask = adapterView.getItemAtPosition(i) as HousekeepingTask;

            var text: String;
            if (item.finished){
                thread{restartTask(item.name)}
                text = "Doch noch nicht fertig ;("
            }
            else{
                thread{finishTask(item.name)}
                text = "Whay geschafft. Weiter so :)"
            }

            Toast.makeText(this, text, Toast.LENGTH_LONG).show()

            item.finished = !item.finished;
            Collections.sort(taskList);
            adapter?.notifyDataSetChanged()

        }

    }

    fun generateData() {

        val result = URL("http://195.201.133.54:8080").readText()
        val gson = Gson()
        val topic = gson.fromJson<Array<HousekeepingTask>>(result, Array<HousekeepingTask>::class.java)
        this@MainActivity.runOnUiThread(java.lang.Runnable {
            this.taskList?.addAll(topic)
            adapter?.notifyDataSetChanged()
        })
    }

    fun finishTask(name: String) {
        URL("http://195.201.133.54:8080/finish/" + name).readText()
    }

    fun restartTask(name: String) {
        URL("http://195.201.133.54:8080/restart/" + name).readText()
    }
}

