package com.bignerdranch.android.criminalintent

import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemSeriousCrimeBinding


abstract class BaseViewHolder<T>(itemView: View) :
    ViewHolder(itemView) {

    abstract fun bind(crime: Crime)

}

class CrimeHolder (
    private val binding: ListItemCrimeBinding
) : BaseViewHolder<Crime>(binding.root) {
    override fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = DateFormat.getPatternInstance("EEEE,MMM d,Y").format(crime.date)

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class SeriousCrimeHolder(
    private val binding: ListItemSeriousCrimeBinding
) : BaseViewHolder<Crime>(binding.root) {
    override fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = DateFormat.getPatternInstance("EEEE,MMM d,Y").format(crime.date)
        //binding.crimePoliceButton.text = "contact police"

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class CrimeListAdapter(
    private val crimes: List<Crime>
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1){
            val binding = ListItemSeriousCrimeBinding.inflate(inflater, parent, false)
            SeriousCrimeHolder(binding)
        } else {
            val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
            CrimeHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val crime = crimes[position]
        holder.bind(crime)
    }

    override fun getItemCount() = crimes.size

    override fun getItemViewType(position: Int): Int {
        val crime = crimes[position]
        return if(crime.requiresPolice){
            1
        } else {
            0
        }
    }
}