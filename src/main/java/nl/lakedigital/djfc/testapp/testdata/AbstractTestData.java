package nl.lakedigital.djfc.testapp.testdata;

import nl.lakedigital.djfc.domain.SoortEntiteit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractTestData<T> {
    protected List<T> entiteiten;

    public abstract void maakEntiteiten();
    public abstract void setSoortEntiteit(T entiteit, SoortEntiteit soortEntiteit, Long entiteitId);

    private void vul() {
        if(entiteiten ==null) {
            entiteiten=new ArrayList<>();
            maakEntiteiten();
        }    }

    public List<T> getEntiteiten() {
        vul();
        return entiteiten;
    }

    public T getEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        vul();

        int nummer =0;
        if(entiteiten.size()>1){
            Random ran = new Random();
            nummer = ran.nextInt(1) + entiteiten.size();
        if(nummer<0){
            nummer = nummer * -1;
        }}

        T entiteit=entiteiten.get(0);

        setSoortEntiteit(entiteit,soortEntiteit,entiteitId);

        return entiteiten.get(0);
    }}
