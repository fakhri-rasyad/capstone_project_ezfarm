package com.c241ps093.ezfarm.ui.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.databinding.FragmentAddBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFragment : DialogFragment() {

    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()

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
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.calendarPick.text = dateFormat.format(selectedDate.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }


}