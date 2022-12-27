package com.alesharik.country.provider.hardcoded;

import com.alesharik.country.provider.Country;
import com.alesharik.country.provider.CountryProvider;
import com.alesharik.country.provider.exception.CountryNotFoundException;
import org.springframework.lang.NonNull;

import java.util.*;

public class HardcodedCountryProvider implements CountryProvider {
    private static final List<Country> COUNTRY_LIST = new ArrayList<>();
    private static final Map<String, Country> COUNTRIES = new HashMap<>();

    static {
        addCountry(new Country("af", "Afghanistan", "https://flagcdn.com/48x36/af.png"));
        addCountry(new Country("al", "Albania", "https://flagcdn.com/48x36/al.png"));
        addCountry(new Country("dz", "Algeria", "https://flagcdn.com/48x36/dz.png"));
        addCountry(new Country("ad", "Andorra", "https://flagcdn.com/48x36/ad.png"));
        addCountry(new Country("ao", "Angola", "https://flagcdn.com/48x36/ao.png"));
        addCountry(new Country("ag", "Antigua and Barbuda", "https://flagcdn.com/48x36/ag.png"));
        addCountry(new Country("ar", "Argentina", "https://flagcdn.com/48x36/ar.png"));
        addCountry(new Country("am", "Armenia", "https://flagcdn.com/48x36/am.png"));
        addCountry(new Country("au", "Australia", "https://flagcdn.com/48x36/au.png"));
        addCountry(new Country("at", "Austria", "https://flagcdn.com/48x36/at.png"));
        addCountry(new Country("az", "Azerbaijan", "https://flagcdn.com/48x36/az.png"));
        addCountry(new Country("bs", "Bahamas", "https://flagcdn.com/48x36/bs.png"));
        addCountry(new Country("bh", "Bahrain", "https://flagcdn.com/48x36/bh.png"));
        addCountry(new Country("bd", "Bangladesh", "https://flagcdn.com/48x36/bd.png"));
        addCountry(new Country("bb", "Barbados", "https://flagcdn.com/48x36/bb.png"));
        addCountry(new Country("by", "Belarus", "https://flagcdn.com/48x36/by.png"));
        addCountry(new Country("be", "Belgium", "https://flagcdn.com/48x36/be.png"));
        addCountry(new Country("bz", "Belize", "https://flagcdn.com/48x36/bz.png"));
        addCountry(new Country("bj", "Benin", "https://flagcdn.com/48x36/bj.png"));
        addCountry(new Country("bt", "Bhutan", "https://flagcdn.com/48x36/bt.png"));
        addCountry(new Country("bo", "Bolivia (Plurinational State of)", "https://flagcdn.com/48x36/bo.png"));
        addCountry(new Country("ba", "Bosnia and Herzegovina", "https://flagcdn.com/48x36/ba.png"));
        addCountry(new Country("bw", "Botswana", "https://flagcdn.com/48x36/bw.png"));
        addCountry(new Country("br", "Brazil", "https://flagcdn.com/48x36/br.png"));
        addCountry(new Country("bn", "Brunei Darussalam", "https://flagcdn.com/48x36/bn.png"));
        addCountry(new Country("bg", "Bulgaria", "https://flagcdn.com/48x36/bg.png"));
        addCountry(new Country("bf", "Burkina Faso", "https://flagcdn.com/48x36/bf.png"));
        addCountry(new Country("bi", "Burundi", "https://flagcdn.com/48x36/bi.png"));
        addCountry(new Country("cv", "Cabo Verde", "https://flagcdn.com/48x36/cv.png"));
        addCountry(new Country("kh", "Cambodia", "https://flagcdn.com/48x36/kh.png"));
        addCountry(new Country("cm", "Cameroon", "https://flagcdn.com/48x36/cm.png"));
        addCountry(new Country("ca", "Canada", "https://flagcdn.com/48x36/ca.png"));
        addCountry(new Country("cf", "Central African Republic", "https://flagcdn.com/48x36/cf.png"));
        addCountry(new Country("td", "Chad", "https://flagcdn.com/48x36/td.png"));
        addCountry(new Country("cl", "Chile", "https://flagcdn.com/48x36/cl.png"));
        addCountry(new Country("cn", "China", "https://flagcdn.com/48x36/cn.png"));
        addCountry(new Country("co", "Colombia", "https://flagcdn.com/48x36/co.png"));
        addCountry(new Country("km", "Comoros", "https://flagcdn.com/48x36/km.png"));
        addCountry(new Country("cg", "Congo", "https://flagcdn.com/48x36/cg.png"));
        addCountry(new Country("cd", "Congo, Democratic Republic of the", "https://flagcdn.com/48x36/cd.png"));
        addCountry(new Country("cr", "Costa Rica", "https://flagcdn.com/48x36/cr.png"));
        addCountry(new Country("ci", "Côte d'Ivoire", "https://flagcdn.com/48x36/ci.png"));
        addCountry(new Country("hr", "Croatia", "https://flagcdn.com/48x36/hr.png"));
        addCountry(new Country("cu", "Cuba", "https://flagcdn.com/48x36/cu.png"));
        addCountry(new Country("cy", "Cyprus", "https://flagcdn.com/48x36/cy.png"));
        addCountry(new Country("cz", "Czechia", "https://flagcdn.com/48x36/cz.png"));
        addCountry(new Country("dk", "Denmark", "https://flagcdn.com/48x36/dk.png"));
        addCountry(new Country("dj", "Djibouti", "https://flagcdn.com/48x36/dj.png"));
        addCountry(new Country("dm", "Dominica", "https://flagcdn.com/48x36/dm.png"));
        addCountry(new Country("do", "Dominican Republic", "https://flagcdn.com/48x36/do.png"));
        addCountry(new Country("ec", "Ecuador", "https://flagcdn.com/48x36/ec.png"));
        addCountry(new Country("eg", "Egypt", "https://flagcdn.com/48x36/eg.png"));
        addCountry(new Country("sv", "El Salvador", "https://flagcdn.com/48x36/sv.png"));
        addCountry(new Country("gq", "Equatorial Guinea", "https://flagcdn.com/48x36/gq.png"));
        addCountry(new Country("er", "Eritrea", "https://flagcdn.com/48x36/er.png"));
        addCountry(new Country("ee", "Estonia", "https://flagcdn.com/48x36/ee.png"));
        addCountry(new Country("sz", "Eswatini", "https://flagcdn.com/48x36/sz.png"));
        addCountry(new Country("et", "Ethiopia", "https://flagcdn.com/48x36/et.png"));
        addCountry(new Country("fj", "Fiji", "https://flagcdn.com/48x36/fj.png"));
        addCountry(new Country("fi", "Finland", "https://flagcdn.com/48x36/fi.png"));
        addCountry(new Country("fr", "France", "https://flagcdn.com/48x36/fr.png"));
        addCountry(new Country("ga", "Gabon", "https://flagcdn.com/48x36/ga.png"));
        addCountry(new Country("gm", "Gambia", "https://flagcdn.com/48x36/gm.png"));
        addCountry(new Country("ge", "Georgia", "https://flagcdn.com/48x36/ge.png"));
        addCountry(new Country("de", "Germany", "https://flagcdn.com/48x36/de.png"));
        addCountry(new Country("gh", "Ghana", "https://flagcdn.com/48x36/gh.png"));
        addCountry(new Country("gr", "Greece", "https://flagcdn.com/48x36/gr.png"));
        addCountry(new Country("gd", "Grenada", "https://flagcdn.com/48x36/gd.png"));
        addCountry(new Country("gt", "Guatemala", "https://flagcdn.com/48x36/gt.png"));
        addCountry(new Country("gn", "Guinea", "https://flagcdn.com/48x36/gn.png"));
        addCountry(new Country("gw", "Guinea-Bissau", "https://flagcdn.com/48x36/gw.png"));
        addCountry(new Country("gy", "Guyana", "https://flagcdn.com/48x36/gy.png"));
        addCountry(new Country("ht", "Haiti", "https://flagcdn.com/48x36/ht.png"));
        addCountry(new Country("hn", "Honduras", "https://flagcdn.com/48x36/hn.png"));
        addCountry(new Country("hu", "Hungary", "https://flagcdn.com/48x36/hu.png"));
        addCountry(new Country("is", "Iceland", "https://flagcdn.com/48x36/is.png"));
        addCountry(new Country("in", "India", "https://flagcdn.com/48x36/in.png"));
        addCountry(new Country("id", "Indonesia", "https://flagcdn.com/48x36/id.png"));
        addCountry(new Country("ir", "Iran (Islamic Republic of)", "https://flagcdn.com/48x36/ir.png"));
        addCountry(new Country("iq", "Iraq", "https://flagcdn.com/48x36/iq.png"));
        addCountry(new Country("ie", "Ireland", "https://flagcdn.com/48x36/ie.png"));
        addCountry(new Country("il", "Israel", "https://flagcdn.com/48x36/il.png"));
        addCountry(new Country("it", "Italy", "https://flagcdn.com/48x36/it.png"));
        addCountry(new Country("jm", "Jamaica", "https://flagcdn.com/48x36/jm.png"));
        addCountry(new Country("jp", "Japan", "https://flagcdn.com/48x36/jp.png"));
        addCountry(new Country("jo", "Jordan", "https://flagcdn.com/48x36/jo.png"));
        addCountry(new Country("kz", "Kazakhstan", "https://flagcdn.com/48x36/kz.png"));
        addCountry(new Country("ke", "Kenya", "https://flagcdn.com/48x36/ke.png"));
        addCountry(new Country("ki", "Kiribati", "https://flagcdn.com/48x36/ki.png"));
        addCountry(new Country("kp", "Korea (Democratic People's Republic of)", "https://flagcdn.com/48x36/kp.png"));
        addCountry(new Country("kr", "Korea, Republic of", "https://flagcdn.com/48x36/kr.png"));
        addCountry(new Country("kw", "Kuwait", "https://flagcdn.com/48x36/kw.png"));
        addCountry(new Country("kg", "Kyrgyzstan", "https://flagcdn.com/48x36/kg.png"));
        addCountry(new Country("la", "Lao People's Democratic Republic", "https://flagcdn.com/48x36/la.png"));
        addCountry(new Country("lv", "Latvia", "https://flagcdn.com/48x36/lv.png"));
        addCountry(new Country("lb", "Lebanon", "https://flagcdn.com/48x36/lb.png"));
        addCountry(new Country("ls", "Lesotho", "https://flagcdn.com/48x36/ls.png"));
        addCountry(new Country("lr", "Liberia", "https://flagcdn.com/48x36/lr.png"));
        addCountry(new Country("ly", "Libya", "https://flagcdn.com/48x36/ly.png"));
        addCountry(new Country("li", "Liechtenstein", "https://flagcdn.com/48x36/li.png"));
        addCountry(new Country("lt", "Lithuania", "https://flagcdn.com/48x36/lt.png"));
        addCountry(new Country("lu", "Luxembourg", "https://flagcdn.com/48x36/lu.png"));
        addCountry(new Country("mg", "Madagascar", "https://flagcdn.com/48x36/mg.png"));
        addCountry(new Country("mw", "Malawi", "https://flagcdn.com/48x36/mw.png"));
        addCountry(new Country("my", "Malaysia", "https://flagcdn.com/48x36/my.png"));
        addCountry(new Country("mv", "Maldives", "https://flagcdn.com/48x36/mv.png"));
        addCountry(new Country("ml", "Mali", "https://flagcdn.com/48x36/ml.png"));
        addCountry(new Country("mt", "Malta", "https://flagcdn.com/48x36/mt.png"));
        addCountry(new Country("mh", "Marshall Islands", "https://flagcdn.com/48x36/mh.png"));
        addCountry(new Country("mr", "Mauritania", "https://flagcdn.com/48x36/mr.png"));
        addCountry(new Country("mu", "Mauritius", "https://flagcdn.com/48x36/mu.png"));
        addCountry(new Country("mx", "Mexico", "https://flagcdn.com/48x36/mx.png"));
        addCountry(new Country("fm", "Micronesia (Federated States of)", "https://flagcdn.com/48x36/fm.png"));
        addCountry(new Country("md", "Moldova, Republic of", "https://flagcdn.com/48x36/md.png"));
        addCountry(new Country("mc", "Monaco", "https://flagcdn.com/48x36/mc.png"));
        addCountry(new Country("mn", "Mongolia", "https://flagcdn.com/48x36/mn.png"));
        addCountry(new Country("me", "Montenegro", "https://flagcdn.com/48x36/me.png"));
        addCountry(new Country("ma", "Morocco", "https://flagcdn.com/48x36/ma.png"));
        addCountry(new Country("mz", "Mozambique", "https://flagcdn.com/48x36/mz.png"));
        addCountry(new Country("mm", "Myanmar", "https://flagcdn.com/48x36/mm.png"));
        addCountry(new Country("na", "Namibia", "https://flagcdn.com/48x36/na.png"));
        addCountry(new Country("nr", "Nauru", "https://flagcdn.com/48x36/nr.png"));
        addCountry(new Country("np", "Nepal", "https://flagcdn.com/48x36/np.png"));
        addCountry(new Country("nl", "Netherlands", "https://flagcdn.com/48x36/nl.png"));
        addCountry(new Country("nz", "New Zealand", "https://flagcdn.com/48x36/nz.png"));
        addCountry(new Country("ni", "Nicaragua", "https://flagcdn.com/48x36/ni.png"));
        addCountry(new Country("ne", "Niger", "https://flagcdn.com/48x36/ne.png"));
        addCountry(new Country("ng", "Nigeria", "https://flagcdn.com/48x36/ng.png"));
        addCountry(new Country("mk", "North Macedonia", "https://flagcdn.com/48x36/mk.png"));
        addCountry(new Country("no", "Norway", "https://flagcdn.com/48x36/no.png"));
        addCountry(new Country("om", "Oman", "https://flagcdn.com/48x36/om.png"));
        addCountry(new Country("pk", "Pakistan", "https://flagcdn.com/48x36/pk.png"));
        addCountry(new Country("pw", "Palau", "https://flagcdn.com/48x36/pw.png"));
        addCountry(new Country("pa", "Panama", "https://flagcdn.com/48x36/pa.png"));
        addCountry(new Country("pg", "Papua New Guinea", "https://flagcdn.com/48x36/pg.png"));
        addCountry(new Country("py", "Paraguay", "https://flagcdn.com/48x36/py.png"));
        addCountry(new Country("pe", "Peru", "https://flagcdn.com/48x36/pe.png"));
        addCountry(new Country("ph", "Philippines", "https://flagcdn.com/48x36/ph.png"));
        addCountry(new Country("pl", "Poland", "https://flagcdn.com/48x36/pl.png"));
        addCountry(new Country("pt", "Portugal", "https://flagcdn.com/48x36/pt.png"));
        addCountry(new Country("qa", "Qatar", "https://flagcdn.com/48x36/qa.png"));
        addCountry(new Country("ro", "Romania", "https://flagcdn.com/48x36/ro.png"));
        addCountry(new Country("ru", "Russian Federation", "https://flagcdn.com/48x36/ru.png"));
        addCountry(new Country("rw", "Rwanda", "https://flagcdn.com/48x36/rw.png"));
        addCountry(new Country("kn", "Saint Kitts and Nevis", "https://flagcdn.com/48x36/kn.png"));
        addCountry(new Country("lc", "Saint Lucia", "https://flagcdn.com/48x36/lc.png"));
        addCountry(new Country("vc", "Saint Vincent and the Grenadines", "https://flagcdn.com/48x36/vc.png"));
        addCountry(new Country("ws", "Samoa", "https://flagcdn.com/48x36/ws.png"));
        addCountry(new Country("sm", "San Marino", "https://flagcdn.com/48x36/sm.png"));
        addCountry(new Country("st", "Sao Tome and Principe", "https://flagcdn.com/48x36/st.png"));
        addCountry(new Country("sa", "Saudi Arabia", "https://flagcdn.com/48x36/sa.png"));
        addCountry(new Country("sn", "Senegal", "https://flagcdn.com/48x36/sn.png"));
        addCountry(new Country("rs", "Serbia", "https://flagcdn.com/48x36/rs.png"));
        addCountry(new Country("sc", "Seychelles", "https://flagcdn.com/48x36/sc.png"));
        addCountry(new Country("sl", "Sierra Leone", "https://flagcdn.com/48x36/sl.png"));
        addCountry(new Country("sg", "Singapore", "https://flagcdn.com/48x36/sg.png"));
        addCountry(new Country("sk", "Slovakia", "https://flagcdn.com/48x36/sk.png"));
        addCountry(new Country("si", "Slovenia", "https://flagcdn.com/48x36/si.png"));
        addCountry(new Country("sb", "Solomon Islands", "https://flagcdn.com/48x36/sb.png"));
        addCountry(new Country("so", "Somalia", "https://flagcdn.com/48x36/so.png"));
        addCountry(new Country("za", "South Africa", "https://flagcdn.com/48x36/za.png"));
        addCountry(new Country("ss", "South Sudan", "https://flagcdn.com/48x36/ss.png"));
        addCountry(new Country("es", "Spain", "https://flagcdn.com/48x36/es.png"));
        addCountry(new Country("lk", "Sri Lanka", "https://flagcdn.com/48x36/lk.png"));
        addCountry(new Country("sd", "Sudan", "https://flagcdn.com/48x36/sd.png"));
        addCountry(new Country("sr", "Suriname", "https://flagcdn.com/48x36/sr.png"));
        addCountry(new Country("se", "Sweden", "https://flagcdn.com/48x36/se.png"));
        addCountry(new Country("ch", "Switzerland", "https://flagcdn.com/48x36/ch.png"));
        addCountry(new Country("sy", "Syrian Arab Republic", "https://flagcdn.com/48x36/sy.png"));
        addCountry(new Country("tj", "Tajikistan", "https://flagcdn.com/48x36/tj.png"));
        addCountry(new Country("tz", "Tanzania, United Republic of", "https://flagcdn.com/48x36/tz.png"));
        addCountry(new Country("th", "Thailand", "https://flagcdn.com/48x36/th.png"));
        addCountry(new Country("tl", "Timor-Leste", "https://flagcdn.com/48x36/tl.png"));
        addCountry(new Country("tg", "Togo", "https://flagcdn.com/48x36/tg.png"));
        addCountry(new Country("to", "Tonga", "https://flagcdn.com/48x36/to.png"));
        addCountry(new Country("tt", "Trinidad and Tobago", "https://flagcdn.com/48x36/tt.png"));
        addCountry(new Country("tn", "Tunisia", "https://flagcdn.com/48x36/tn.png"));
        addCountry(new Country("tr", "Turkey", "https://flagcdn.com/48x36/tr.png"));
        addCountry(new Country("tm", "Turkmenistan", "https://flagcdn.com/48x36/tm.png"));
        addCountry(new Country("tv", "Tuvalu", "https://flagcdn.com/48x36/tv.png"));
        addCountry(new Country("ug", "Uganda", "https://flagcdn.com/48x36/ug.png"));
        addCountry(new Country("ua", "Ukraine", "https://flagcdn.com/48x36/ua.png"));
        addCountry(new Country("ae", "United Arab Emirates", "https://flagcdn.com/48x36/ae.png"));
        addCountry(new Country("gb", "United Kingdom", "https://flagcdn.com/48x36/gb.png"));
        addCountry(new Country("us", "United States of America", "https://flagcdn.com/48x36/us.png"));
        addCountry(new Country("uy", "Uruguay", "https://flagcdn.com/48x36/uy.png"));
        addCountry(new Country("uz", "Uzbekistan", "https://flagcdn.com/48x36/uz.png"));
        addCountry(new Country("vu", "Vanuatu", "https://flagcdn.com/48x36/vu.png"));
        addCountry(new Country("ve", "Venezuela (Bolivarian Republic of)", "https://flagcdn.com/48x36/ve.png"));
        addCountry(new Country("vn", "Viet Nam", "https://flagcdn.com/48x36/vn.png"));
        addCountry(new Country("ye", "Yemen", "https://flagcdn.com/48x36/ye.png"));
        addCountry(new Country("zm", "Zambia", "https://flagcdn.com/48x36/zm.png"));
        addCountry(new Country("zw", "Zimbabwe", "https://flagcdn.com/48x36/zw.png"));

    }

    private static void addCountry(Country country) {
        COUNTRIES.put(country.code(), country);
        COUNTRY_LIST.add(country);
    }

    @NonNull
    @Override
    public List<Country> getCountries() {
        return Collections.unmodifiableList(COUNTRY_LIST);
    }

    @NonNull
    @Override
    public Country getCountry(@NonNull String code) {
        var country = COUNTRIES.get(code.toLowerCase());
        if (country == null)
            throw new CountryNotFoundException(code);
        return country;
    }

    @Override
    public boolean hasCountry(@NonNull String code) {
        return COUNTRIES.containsKey(code.toLowerCase());
    }
}
