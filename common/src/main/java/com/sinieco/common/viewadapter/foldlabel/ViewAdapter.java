package com.sinieco.common.viewadapter.foldlabel;

import androidx.databinding.BindingAdapter;
import android.view.View;

import com.sinieco.arch.binding.command.BindingCommand;
import com.sinieco.common.widget.fold_label.FoldLabelLayout;
import com.sinieco.common.widget.fold_label.LabelOptionBean;

import java.util.List;

/**
 * 可折叠的标签选择器
 * Created by LiuHe on 2018/9/18.
 */

public class ViewAdapter {

    @BindingAdapter(value = {"labelOptionBeans", "labelTags", "selectedFirstLabel", "onLabelSelectCommand"}, requireAll = false)
    public static void setLabel(FoldLabelLayout foldLabelLayout, List<LabelOptionBean> beans, List labelTags, boolean selectedFirstLabel, final BindingCommand selectedCommand) {
        foldLabelLayout.setOptions(beans);
        if (labelTags != null) {
            foldLabelLayout.setTags(labelTags);
        }
        if (selectedFirstLabel) {
            foldLabelLayout.setLabelSelected(0);
        }
        foldLabelLayout.setOnLabelSelectedListener(new FoldLabelLayout.OnLabelSelectedListener() {
            @Override
            public void onLabelSelected(View view, int state) {
                if (selectedCommand != null) {
                    selectedCommand.execute(view.getTag());
                }
            }
        });
    }

    @BindingAdapter(value = {"labelPosition"}, requireAll = false)
    public static void setLabelPosition(FoldLabelLayout foldLabelLayout, Integer position) {
        if (position != null) {
            foldLabelLayout.setLabelSelected(position);
        }
    }

}
