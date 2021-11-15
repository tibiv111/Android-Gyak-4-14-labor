package com.example.quizapp.models
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.MainActivity
import com.example.quizapp.R

class questionAdapter(private val questionList: List<Question>,
                        private val listener: onItemClickListener
                      ) : RecyclerView.Adapter<questionAdapter.QuestionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.question_item,
            parent, false)
        return QuestionViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val currentItem = questionList[position]

        holder.questionText.text = currentItem.question
        holder.answer.text = currentItem.answers[0]
        holder.questionType.text = currentItem.questionType

    }

    override fun getItemCount() = questionList.size


    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val questionText : TextView = itemView.findViewById(R.id.questionItem_qText)
        val questionType : TextView = itemView.findViewById(R.id.questionItem_qType)
        val answer : TextView = itemView.findViewById(R.id.questionItem_qAnswer)
        val questionDetails : Button = itemView.findViewById(R.id.questionItem_detailsBtn)
        val questionDelete : Button = itemView.findViewById(R.id.questionItem_deleteBtn)

        init {
            questionDelete.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION)
            {
                listener.onItemClick(position)
            }

        }


    }
    interface onItemClickListener
    {
        fun onItemClick(position: Int)

    }

}