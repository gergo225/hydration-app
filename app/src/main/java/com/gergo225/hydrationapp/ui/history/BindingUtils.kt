package com.gergo225.hydrationapp.ui.history

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("hydrationDateFormatted")
fun TextView.setHydrationDateFormatted(item: DailyHydrationItem) {
    // TODO: Custom formatting for date
    val formatter = SimpleDateFormat("EEEE, d MMMM", Locale.ENGLISH)
    text = formatter.format(item.dayDate)
}

@BindingAdapter("hydrationAmountFormatted")
fun TextView.setHydrationAmountFormatted(item: DailyHydrationItem) {
    // TODO: Custom formatting for amount
    text = item.hydrationMl.toString().plus(" ml")
}

@BindingAdapter("hydrationPercentageFormatted")
fun TextView.setHydrationPercentageFormatted(item: DailyHydrationItem) {
    // TODO: Custom formatting for percentage
    val formatting = "%d%% out of %d ml Goal"
    val percentage = (item.hydrationMl.toFloat() * 100 / item.hydrationGoal).toInt()
    text = String.format(formatting, percentage, item.hydrationGoal)
}

@BindingAdapter("hydrationGoalReached")
fun ImageView.setHydrationGoalReached(item: DailyHydrationItem) {
    visibility = if (item.hydrationMl >= item.hydrationGoal) View.VISIBLE else View.INVISIBLE
}
