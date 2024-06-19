package com.c241ps093.ezfarm.ui.detail

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.data.database.PlantTodo
import com.c241ps093.ezfarm.databinding.ActivityDetailBinding
import com.c241ps093.ezfarm.dateFormat
import com.c241ps093.ezfarm.makeToast
import com.c241ps093.ezfarm.receiver.AlarmReceiver
import com.c241ps093.ezfarm.ui.timepicker.TimePickerFragment
import com.c241ps093.ezfarm.viewmodel.factory.ViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

class DetailActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel


    private lateinit var alarmReceiver: AlarmReceiver

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                makeToast(this, "Notifications permission granted")
            } else {
                makeToast(this, "Notifications permission rejected")
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel(this)

        val plantData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PLANT_DATA, Plant::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(PLANT_DATA)
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        alarmReceiver = AlarmReceiver()

        binding.setNotificationButton.setOnClickListener {
            val timePickerFragmentOne = TimePickerFragment()
            timePickerFragmentOne.show(supportFragmentManager, ALARM_TAG)
        }

        if (plantData != null) {
            val plantId = plantData.id
            val todoDate = getDayDifference(plantData)
            viewModel.checkTodo(plantId).observe(this){todoExist ->
                if(!todoExist){
                    viewModel.getTodoList(plantId)
                }
                viewModel.getTodoFromDatabase(plantId, todoDate).observe(this){ listOfPlantTodo ->

                    Log.e("DETAIL", listOfPlantTodo.toString())
                    if(listOfPlantTodo.isNotEmpty()){
                        binding.progressBar.visibility = View.INVISIBLE
                        setUpRecyclerView(listOfPlantTodo)
                        binding.apply {
                            plantName.text = plantData.plantType
                            plantStatus.text = plantData.growthStatus
                            dayTitle.text = getString(R.string.day, listOfPlantTodo[0].toDoDay.toString())
                            backButton.setOnClickListener {
                                finish()
                            }
                        }
                    }
                }
            }

        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val time = dateFormat.format(calendar.time)
        val title = "EZFarm notification"
        val message = "Time to care for your plants"
        alarmReceiver.setOneTimeAlarm(this, title, time, message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayDifference(plantData: Plant?): Int {
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        val today = LocalDate.now()
        val plantedDate = LocalDate.parse(plantData?.plantedDate, formatter)
        return (ChronoUnit.DAYS.between(plantedDate, today) + 1).toInt()
    }

    private fun setUpRecyclerView(todoList: List<PlantTodo?>?) {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvStorylist.apply {
            layoutManager = linearLayoutManager
            adapter = DetailRecyclerAdapter(todoList, ::updateTodo)
        }
    }

    private fun updateTodo(isCompleted: Boolean, plantTodo: PlantTodo) {
        viewModel.updateTodoList(
            isCompleted,
            plantTodo
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getViewModel(appCompatActivity: AppCompatActivity): DetailViewModel {
        val viewModelFactory = ViewModelFactory.getInstance(appCompatActivity.application)
        return ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
    }

    companion object {
        const val PLANT_DATA = "plant_data"
        private const val ALARM_TAG = "ALARM_SET"
    }
}