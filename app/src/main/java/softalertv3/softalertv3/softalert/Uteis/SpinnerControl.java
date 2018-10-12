package softalertv3.softalertv3.softalert.Uteis;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class SpinnerControl {

    /**
     *
     *
     * FORMATANDO A COR : "#" + Integer.toHexString(ContextCompat.getColor(SUA_ACTIVITY.this, R.color.SUA_COR))
     *
     * @param hint = refere-se ao item de explicação, se não quiser é só deixar false, se sim, a função vai considerar o primeiro elemento da lista como o elemento hint
     * @param spinner
     * @param iv = imagem que aparece ao lado do spinner
     * @param listaOriginal
     * @param corSelecionada = necessário mandar a cor formatada em base hexadecimal. Ver a parte superior do método
     * @param corNaoSelecionada = necessário mandar a cor formatada em base hexadecimal. Ver a parte superior do método
     */
    public void configurarSpinnerControl(final boolean hint, final MaterialSpinner spinner, final ImageView iv, final List<String> listaOriginal, final String corSelecionada, final String corNaoSelecionada)
    {
        final String hintItem = listaOriginal.get(0);

        spinner.setItems(listaOriginal);

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                iv.setColorFilter(Color.parseColor(corNaoSelecionada),android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });

        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                iv.setColorFilter(Color.parseColor(corNaoSelecionada),android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hint && spinner.getItems().size() == listaOriginal.size()) {
                    String itemSelecionado = spinner.getItems().get(spinner.getSelectedIndex()).toString();

                    if (spinner.getItems().contains(hintItem) && !itemSelecionado.equals(hintItem)) {

                        List<String> novaLista = new ArrayList<>();

                        int i = 0;
                        for (String item : listaOriginal) {
                            if (i++ != 0)
                                novaLista.add(item);
                        }

                        spinner.setItems(novaLista);

                        for (i = 0; i < spinner.getItems().size(); i++) {
                            if (spinner.getItems().get(i).toString().equals(itemSelecionado)) {
                                spinner.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                }

                iv.setColorFilter(Color.parseColor(corSelecionada), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });
    }

}
