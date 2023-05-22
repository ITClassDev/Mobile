package ru.slavapmk.shtp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.Values;
import ru.slavapmk.shtp.databinding.FragmentEventsBinding;

public class EventsFragment extends Fragment {
    private FragmentEventsBinding binding;

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventsBinding.inflate(inflater);

        String events = "{\"operationName\":null,\"variables\":{\"onlyActual\":true,\"pageNumber\":1,\"search\":\"\",\"startDate\":\"2022-12-30\",\"portalIds\":[45],\"districtIds\":[],\"agentIds\":[],\"formeIds\":[],\"audienceIds\":[],\"subjectIds\":[],\"participationTypes\":[]},\"query\":\"query ($portalIds: [Int!], $startDate: String, $finishDate: String, $agentIds: [Int!], $districtIds: [Int!], $formeIds: [Int!], $audienceIds: [Int!], $subjectIds: [Int!], $search: String, $pageNumber: Int, $onlyActual: Boolean, $orderDays: String, $archive: Boolean, $elasticsearch: Boolean, $participationTypes: [Int!]) {\\n  eventsList(portalIds: $portalIds, startDate: $startDate, finishDate: $finishDate, agentIds: $agentIds, districtIds: $districtIds, formeIds: $formeIds, audienceIds: $audienceIds, subjectIds: $subjectIds, search: $search, pageNumber: $pageNumber, onlyActual: $onlyActual, orderDays: $orderDays, archive: $archive, elasticsearch: $elasticsearch, participationTypes: $participationTypes) {\\n    pagesCount\\n    maxArchiveStartDate\\n    maxArchiveFinishDate\\n    selectedStartDate\\n    selectedFinishDate\\n    events {\\n      id\\n      title\\n      seats\\n      reservedSeats\\n      emptySeats\\n      additionalSeats\\n      emptySeatsOnline\\n      reservedSeatsOnline\\n      seatsOnline\\n      date\\n      startTime\\n      finishedTime\\n      startRegistration\\n      finishedRegistration\\n      markEvent\\n      audiencesShort\\n      participationTypes\\n      comments {\\n        id\\n        reaction {\\n          id\\n          __typename\\n        }\\n        __typename\\n      }\\n      portal {\\n        id\\n        name\\n        logoImage\\n        host\\n        markEventText\\n        markEventImage\\n        __typename\\n      }\\n      audiences {\\n        id\\n        name\\n        __typename\\n      }\\n      formes {\\n        id\\n        name\\n        __typename\\n      }\\n      subject {\\n        id\\n        name\\n        __typename\\n      }\\n      agent {\\n        id\\n        name\\n        logoImage\\n        logo {\\n          large\\n          medium\\n          __typename\\n        }\\n        __typename\\n      }\\n      house {\\n        address\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), events);


        binding.eveeeeend.setLayoutManager(new LinearLayoutManager(requireContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_20
        )));
        binding.eveeeeend.addItemDecoration(dividerItemDecoration);


        Values.INSTANCE.getEventsApi().getEvents(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(mosEvents -> binding.eveeeeend.setAdapter(
                        new EventsAdapter(mosEvents.getData().getEventsList().getEvents())
                ));

        return binding.getRoot();
    }
}
