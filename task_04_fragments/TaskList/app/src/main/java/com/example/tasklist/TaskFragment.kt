package com.example.tasklist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.TaskAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tasks: ArrayList<Task>
    lateinit var names: ArrayList<String>
    lateinit var descriptions: ArrayList<String>
    lateinit var datas: ArrayList<String>
    lateinit var statuses: ArrayList<String>
    private lateinit var mContext : Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        val manager = LinearLayoutManager(mContext)
        recyclerView = view.findViewById(R.id.recycle_view)
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        adapter = TaskAdapter(tasks)
        recyclerView.adapter = adapter
    }

    private fun  initialize() {
        tasks = arrayListOf<Task>()
        names = arrayListOf(
            getString(R.string.name_1),
            getString(R.string.name_2),
            getString(R.string.name_3)
        )
        descriptions = arrayListOf(
            getString(R.string.description_1),
            getString(R.string.description_2),
            getString(R.string.description_3)
        )
        datas = arrayListOf(
            getString(R.string.data_1),
            getString(R.string.data_2),
            getString(R.string.data_3)
        )
        statuses = arrayListOf(
            getString(R.string.status_1),
            getString(R.string.status_2),
            getString(R.string.status_3)
        )
        for (i in names.indices) {
            tasks.add(Task(names[i], descriptions[i], datas[i], statuses[i]))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}