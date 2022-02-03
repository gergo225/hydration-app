package com.gergo225.hydrationapp.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import com.gergo225.hydrationapp.R
import com.gergo225.hydrationapp.databinding.FragmentHistoryBinding
import com.gergo225.hydrationapp.repository.database.HydrationDatabase
import com.gergo225.hydrationapp.repository.preferences.UserPreferences
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlin.math.max

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private var _binding: FragmentHistoryBinding? = null

    private lateinit var chart: BarChart

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val dao = HydrationDatabase.getInstance(application).hydrationDatabaseDao
        val userPreferences = UserPreferences(application)
        val historyViewModelFactory = HistoryViewModelFactory(dao, userPreferences)
        historyViewModel =
            ViewModelProvider(this, historyViewModelFactory).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        chart = binding.historyChart
        setupChart()

        // History list
        val adapter = DailyHydrationAdapter()
        binding.hydrationHistoryList.adapter = adapter

        historyViewModel.last30DaysHydrationItems.distinctUntilChanged()
            .observe(viewLifecycleOwner, Observer {
                Log.i("HistoryFragment", "Last 30 days: $it")
                adapter.submitList(it)
                setChartData(it)
            })

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupChart() {
        chart.legend.isEnabled = false
        chart.setDrawValueAboveBar(false)
        chart.description.isEnabled = false
        chart.setMaxVisibleValueCount(30)
        chart.setDrawGridBackground(false)

        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.setDrawAxisLine(false)
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.setDrawLabels(false)

        chart.axisRight.isEnabled = false

        chart.axisLeft.setDrawAxisLine(false)
        chart.axisLeft.setDrawGridLinesBehindData(true)
        chart.axisLeft.gridLineWidth = 1f
        chart.axisLeft.setDrawZeroLine(true)
        chart.axisLeft.zeroLineWidth = 2f
        chart.axisLeft.zeroLineColor = resources.getColor(R.color.grey, null)
        chart.axisLeft.labelCount = 4
        chart.axisLeft.textColor = resources.getColor(R.color.white, null)
        chart.axisLeft.spaceBottom = 0f
        chart.axisLeft.spaceTop = 0f
        chart.axisLeft.gridColor = resources.getColor(R.color.light_grey, null)

        // TODO: Max label should be 2200


        chart.setTouchEnabled(false)

    }

    private fun setChartData(history: List<DailyHydrationItem>?) {
        if (history == null) return

        val values = ArrayList<BarEntry>()
        val colors = ArrayList<Int>()
        val goalValue = history.first().hydrationGoal

        // TODO: When less than 30 values -> set empty bars to fill remaining space

        for ((index, hydration) in history.withIndex()) {
            val amount = hydration.hydrationMl
            values.add(
                BarEntry(
                    index.toFloat(),
                    floatArrayOf(amount.toFloat(), max((goalValue - amount).toFloat(), 0f))
                )
            )

            colors.add(
                resources.getColor(
                    if (amount >= goalValue) R.color.green else R.color.yellow,
                    null
                )
            )
            colors.add(resources.getColor(R.color.grey, null))

        }

        val set1 = BarDataSet(values, "Hydration history for last 30 days")
        set1.colors = colors
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        val data = BarData(dataSets)
        data.barWidth = 0.4f

        chart.data = data
        chart.invalidate()
    }
}