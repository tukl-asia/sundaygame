package com.sunday.engine.scenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.sunday.engine.databank.DataBank;
import com.sunday.engine.event.Event;
import com.sunday.engine.examples.Role;

import java.util.ArrayList;

public class Scenario implements Disposable {
    private DataBank dataBank;
    private ScopeType scopeType;
    private Scenario parent;
    private ArrayList<Scenario> kids = new ArrayList<>();
    private ArrayList<Role> roles = new ArrayList<>();

    public Scenario(ScopeType scopeType, DataBank dataBank) {
        this.scopeType = scopeType;
        parent = null;
        this.dataBank = dataBank;
    }

    public void addKid(Scenario scenario) {
        if (!kids.contains(scenario)) {
            kids.add(scenario);
            scenario.parent = this;
        }
    }

    public void removeKid(Scenario scenario) {
        if (kids.contains(scenario)) {
            scenario.parent = null;
            kids.remove(scenario);
        }
    }

    public ArrayList<Scenario> getKids() {
        return kids;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.abstractModel.connectToDataBank(dataBank);
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void notifyAllRoles(Event event) {
        Gdx.app.log("Scenario", "Receive a Event : " + event.toString());
        for (Role role : roles) {
            role.abstractModel.notifyEvent(event);
        }
    }

    @Override
    public void dispose() {
        scopeType = null;
        parent = null;
        kids.forEach(e -> e.dispose());
        kids.clear();
        roles.forEach(e -> e.dispose());
        roles.clear();
    }
}