package com.c241ps093.ezfarm.ui.add

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.LocaleData
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.databinding.FragmentAddBinding
import com.c241ps093.ezfarm.dateFormat
import com.c241ps093.ezfarm.makeToast
import com.c241ps093.ezfarm.ui.home.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private var addPlant: AddPlant? = null

    private var plantType: String? = null

    private var plantPlantedDate: String? = null

    private var plantHarvestDate: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plants = requireContext().resources.getStringArray(R.array.Plants)
        plantType = plants[0]

        binding.apply {
            cancelButton.setOnClickListener {
                dismiss()
            }
//            calendarPick.setOnClickListener {
//                showDatePicker()
//            }
            setupPlantDate()
            spinner.adapter = ArrayAdapter(
                requireContext(),
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                plants
            )

            spinner.onItemSelectedListener = object : OnItemSelectedListener {
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
                if (plantPlantedDate == null) {
                    makeToast(requireContext(), getString(R.string.date_empty_error))
                    return@setOnClickListener
                } else if (plantType == null) {
                    makeToast(requireContext(), getString(R.string.plant_type_empty_error))
                    return@setOnClickListener
                }
                val newDummyData = Plant(
                    plantedDate = plantPlantedDate.toString(),
                    harvestDate = plantHarvestDate.toString(),
                    growthStatus = "Seeding",
                    plantType = plantType.toString()
                )
                addPlant?.addPlant(newDummyData)
                dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val parent = requireActivity()
        if (parent is HomeActivity) {
            this.addPlant = parent.addPlant
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.addPlant = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = LocalDate.of(year, monthOfYear, dayOfMonth)
                val formatter = DateTimeFormatter.ofPattern(dateFormat)
                plantPlantedDate = selectedDate.format(formatter)
                binding.calendarPick.text = plantPlantedDate
                plantHarvestDate = selectedDate.plusDays(90).format(formatter)
            },
            LocalDateTime.now().year,
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth,
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupPlantDate(){
        val today = LocalDateTime.now()
        val selectedDate = LocalDate.of(today.year, today.month, today.dayOfMonth)
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        plantPlantedDate = selectedDate.format(formatter)
        binding.calendarPick.text = plantPlantedDate
        plantHarvestDate = selectedDate.plusDays(90).format(formatter)
    }

    interface AddPlant {
        fun addPlant(newPlant: Plant)
    }
}


