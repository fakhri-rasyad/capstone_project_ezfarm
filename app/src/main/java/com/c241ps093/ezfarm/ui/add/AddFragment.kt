package com.c241ps093.ezfarm.ui.add

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.databinding.FragmentAddBinding
import com.c241ps093.ezfarm.dateFormatter
import com.c241ps093.ezfarm.makeToast
import com.c241ps093.ezfarm.ui.home.DummyData
import com.c241ps093.ezfarm.ui.home.HomeActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddFragment : DialogFragment() {

    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()

    private var addPlant: AddPlant? = null

    private var plantType: String? = null

    private var plantPlantedDate: Date? = null

    private var plantHarvestDate: Date? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plants = requireContext().resources.getStringArray(R.array.Plants)
        plantType = plants[0]

        binding.apply {
            cancelButton.setOnClickListener {
                dismiss()
            }
            calendarPick.setOnClickListener {
                showDatePicker()
            }
            spinner.adapter = ArrayAdapter(
                requireContext(),
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                plants)

            spinner.onItemSelectedListener = object : OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    plantType = spinner.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            addButton.setOnClickListener {
                if(plantPlantedDate == null){
                    makeToast(requireContext(), getString(R.string.date_empty_error))
                    return@setOnClickListener
                } else if (plantType == null){
                    makeToast(requireContext(), getString(R.string.plant_type_empty_error))
                    return@setOnClickListener
                }
                val newDummyData = Plant(
                    plantedDate = plantPlantedDate as Date,
                    harvestDate =  plantHarvestDate as Date,
                    growthStatus =  "Seeding",
                    plantType =  plantType.toString())
                addPlant?.addPlant(newDummyData)
                dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val parent = requireActivity()
        if(parent is HomeActivity){
            this.addPlant = parent.addPlant
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.addPlant = null
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                plantPlantedDate = selectedDate.time
                binding.calendarPick.text = dateFormatter(selectedDate.time)
                selectedDate.add(Calendar.DAY_OF_MONTH, 90)
                plantHarvestDate = selectedDate.time
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    interface AddPlant {
        fun addPlant(newPlant : Plant)
    }
}


