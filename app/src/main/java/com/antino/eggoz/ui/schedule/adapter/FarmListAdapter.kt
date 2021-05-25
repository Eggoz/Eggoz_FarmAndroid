package com.antino.eggoz.ui.schedule.adapter

class FarmListAdapter(
    /*private val contextmain: MainActivity,
    private val farmlist: List<Farm.Result>?=null*/
) /*: RecyclerView.Adapter<FarmListAdapter.ViewHolder>() {
    private lateinit var listItem: View



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_schedulefarm_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return farmlist?.size!!
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_farmName.text=farmlist!![position].farmName
        holder.txt_No_of_Grower.text=farmlist[position].numberOfGrowerShed.toString()
        holder.txt_No_of_Sheds.text=farmlist[position].numberOfLayerShed.toString()

        holder.btn_addSechdule.setOnClickListener { contextmain.scheduleDetail(farmlist[position].id) }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_farmName: TextView = itemView.findViewById(R.id.txt_farm_name)
        val txt_No_of_Grower: TextView = itemView.findViewById(R.id.txt_No_of_Grower)
        val txt_No_of_Sheds: TextView = itemView.findViewById(R.id.txt_No_of_Sheds)


        val btn_addSechdule: Button = itemView.findViewById(R.id.btn_addSechdule)







    }
}*/